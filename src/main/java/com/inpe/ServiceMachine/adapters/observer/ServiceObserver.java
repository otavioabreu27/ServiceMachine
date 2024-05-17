package com.inpe.ServiceMachine.adapters.observer;

import com.inpe.ServiceMachine.adapters.enums.ServiceStatus;
import com.inpe.ServiceMachine.drivers.repository.ServiceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.concurrent.TimeUnit;

public class ServiceObserver implements Runnable {
    private final ServiceRepository repository;
    private final String healthUrl;
    private final String hash;
    private final String name;
    private final int maxRetries = 5;
    private volatile boolean running = true;
    private static final Logger logger = LoggerFactory.getLogger(ServiceObserver.class);

    public ServiceObserver(ServiceRepository repository, String healthUrl, String hash, String name) {
        this.repository = repository;
        this.healthUrl = healthUrl;
        this.hash = hash;
        this.name = name;
    }

    public void stop() {
        running = false;
    }

    @Override
    public void run() {
        while (running) {
            try {
                logger.info("{} - Is alive", String.format("%s:%s", this.name, this.hash));
                if (!checkServiceAlive()) {
                    handleServiceUnavailable();
                }
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private boolean checkServiceAlive() {
        try {
            URL url = new URI(healthUrl).toURL();
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            return (con.getResponseCode() == HttpURLConnection.HTTP_OK && readResponse(con).equals(this.hash));
        } catch (IOException e) {
            return false;
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    private void handleServiceUnavailable() {
        repository.updateServiceStatus(hash, name, ServiceStatus.RECONNECTION);
        logger.info("{} - Not responding, trying again in 5 seconds", String.format("%s:%s", this.name, this.hash));
        for (int i = 0; i < maxRetries; i++) {
            try {
                TimeUnit.SECONDS.sleep(5);
                if (checkServiceAlive()) {
                    repository.updateServiceStatus(hash, name, ServiceStatus.OK);
                    return;
                }
                if (i + 1 == maxRetries) {
                    logger.info("{} - Max retries reached out", String.format("%s:%s", this.name, this.hash));
                } else {
                    logger.info("{} - Retry {}...", String.format("%s:%s", this.name, this.hash), i+1);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        repository.updateServiceStatus(hash, name, ServiceStatus.OFF);
        logger.info("{} - Died", String.format("%s:%s", this.name, this.hash));
        stop();
    }

    private String readResponse(HttpURLConnection con) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            return response.toString();
        }
    }
}
