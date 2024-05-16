package com.inpe.ServiceMachine.drivers.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.inpe.ServiceMachine.adapters.dto.CreateServiceDTO;
import com.inpe.ServiceMachine.adapters.dto.ReadServiceDTO;
import com.inpe.ServiceMachine.adapters.enums.ServiceStatus;
import com.inpe.ServiceMachine.drivers.generators.GeneratorHash;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import java.util.Set;

@Component
public class ServiceRepositoryRedis extends ServiceRepository {
    private final Jedis jedis;
    private final ObjectMapper mapper;

    public ServiceRepositoryRedis() {
        this.jedis = new Jedis("localhost", 6379);
        this.mapper = new ObjectMapper();
    }

    public ServiceRepositoryRedis(String redisHost, int redisPort) {
        this.jedis = new Jedis(redisHost, redisPort);
        this.mapper = new ObjectMapper();
    }

    @Override
    public void addService(CreateServiceDTO service) {
        String hashInput = service.toString();
        String hash = GeneratorHash.generate(hashInput);

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

    @Override
    public ReadServiceDTO removeService(String serviceHash) {
        return null;
    }

    @Override
    public ReadServiceDTO[] getServicesByName(String serviceName) {
        return new ReadServiceDTO[0];
    }

    @Override
    public ReadServiceDTO[] getServicesByStatus(ServiceStatus serviceStatus) {
        return new ReadServiceDTO[0];
    }

    @Override
    public ReadServiceDTO[] getServicesByNameAndStatus(String serviceName, ServiceStatus serviceStatus) {
        return new ReadServiceDTO[0];
    }

    @Override
    public ReadServiceDTO[] getAllServices() {
        return new ReadServiceDTO[0];
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
