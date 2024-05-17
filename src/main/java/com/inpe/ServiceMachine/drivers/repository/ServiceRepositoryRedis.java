package com.inpe.ServiceMachine.drivers.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.inpe.ServiceMachine.adapters.dto.CreateServiceDTO;
import com.inpe.ServiceMachine.adapters.dto.ReadServiceDTO;
import com.inpe.ServiceMachine.adapters.enums.ServiceStatus;
import com.inpe.ServiceMachine.drivers.generators.GeneratorHash;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import java.util.Set;

@Component
public class ServiceRepositoryRedis extends ServiceRepository {
    private final Jedis jedis;
    private final ObjectMapper mapper;
    private final Logger logger = LoggerFactory.getLogger(ServiceRepositoryRedis.class);

    public ServiceRepositoryRedis() {
        this.jedis = new Jedis("redis", 6379);
        this.mapper = new ObjectMapper();
    }

    public ServiceRepositoryRedis(String redisHost, int redisPort) {
        this.jedis = new Jedis(redisHost, redisPort);
        this.mapper = new ObjectMapper();
    }

    @Override
    public String addService(CreateServiceDTO service) {
        String hashInput = service.toString();
        String hash = GeneratorHash.generate(hashInput);
        logger.info("Input: {} / Hash: {}", hashInput, hash);

        // Check if service already exists
        String pattern = String.format("%s:*:%s", service.getName(), hash);
        Set<String> keys = jedis.keys(pattern);

        if (!keys.isEmpty()) {
            // If exists update its state to ok
            this.updateServiceStatus(hash, service.getName(), ServiceStatus.OK);
        } else {
            // If not, register it
            ReadServiceDTO serviceToRedis = new ReadServiceDTO(
                    hash,
                    service.getAddress(),
                    service.getPort(),
                    service.getName(),
                    service.getHealthUrl(),
                    ServiceStatus.OK
            );

            try {
                String serviceJson = this.mapper.writeValueAsString(serviceToRedis);
                String redisKey = String.format("%s:%s:%s", service.getName(), ServiceStatus.OK, hash);

                this.jedis.set(redisKey, serviceJson);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        return hash;
    }

    @Override
    public ReadServiceDTO removeService(String serviceHash) {
        return null;
    }

    @Override
    public ReadServiceDTO[] getServicesByName(String serviceName) {
        String pattern = String.format("%s:*", serviceName);
        Set<String> keys = jedis.keys(pattern);

        ReadServiceDTO[] services = new ReadServiceDTO[keys.size()];
        int index = 0;
        for (String key : keys) {
            String serviceJson = this.jedis.get(key);
            try {
                ReadServiceDTO service = this.mapper.readValue(serviceJson, ReadServiceDTO.class);
                services[index++] = service;
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }

        return services;
    }

    @Override
    public ReadServiceDTO[] getServicesByStatus(ServiceStatus serviceStatus) {
        String pattern = String.format("*:%s:*", serviceStatus);
        Set<String> keys = jedis.keys(pattern);

        ReadServiceDTO[] services = new ReadServiceDTO[keys.size()];
        int index = 0;
        for (String key : keys) {
            String serviceJson = this.jedis.get(key);
            try {
                ReadServiceDTO service = this.mapper.readValue(serviceJson, ReadServiceDTO.class);
                services[index++] = service;
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }

        return services;
    }

    @Override
    public ReadServiceDTO[] getServicesByNameAndStatus(String serviceName, ServiceStatus serviceStatus) {
        String pattern = String.format("%s:%s:*", serviceName, serviceStatus);
        Set<String> keys = jedis.keys(pattern);

        ReadServiceDTO[] services = new ReadServiceDTO[keys.size()];
        int index = 0;
        for (String key : keys) {
            String serviceJson = this.jedis.get(key);
            try {
                ReadServiceDTO service = this.mapper.readValue(serviceJson, ReadServiceDTO.class);
                services[index++] = service;
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }

        return services;
    }

    @Override
    public ReadServiceDTO[] getAllServices() {
        Set<String> keys = jedis.keys("*");

        ReadServiceDTO[] services = new ReadServiceDTO[keys.size()];
        int index = 0;
        for (String key : keys) {
            String serviceJson = this.jedis.get(key);
            try {
                ReadServiceDTO service = this.mapper.readValue(serviceJson, ReadServiceDTO.class);
                services[index++] = service;
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }

        return services;
    }

    @Override
    public void updateServiceStatus(String serviceHash, String serviceName, ServiceStatus newServiceStatus) {
        String pattern = String.format("%s:*:%s", serviceName, serviceHash);
        Set<String> keys = jedis.keys(pattern);
        String content = null;

        if (!keys.isEmpty()) {
            for (String key : keys) {
                if (content == null) {
                    content = jedis.getDel(key);
                }

                jedis.del(key);
            }

            String newKey = String.format("%s:%s:%s", serviceName, newServiceStatus, serviceHash);
            jedis.set(newKey, content);

            if (newServiceStatus != ServiceStatus.OK) {
                jedis.expire(newKey, 259200);
            }
        }
    }
}
