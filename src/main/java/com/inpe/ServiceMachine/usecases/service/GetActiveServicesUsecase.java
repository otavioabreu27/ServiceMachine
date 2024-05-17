package com.inpe.ServiceMachine.usecases.service;

import com.inpe.ServiceMachine.adapters.dto.ReadServiceDTO;
import com.inpe.ServiceMachine.adapters.enums.ServiceStatus;
import com.inpe.ServiceMachine.adapters.gateways.ServiceGateway;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class GetActiveServicesUsecase {
    private final ServiceGateway gateway;
    private final Map<String, Integer> currentServiceLength = new ConcurrentHashMap<>();
    private final Map<String, Integer> lastServiceIndex = new ConcurrentHashMap<>();

    protected GetActiveServicesUsecase(ServiceGateway gateway) {
        this.gateway = gateway;
    }

    public ReadServiceDTO[] get() {
        return this.gateway.getServicesByStatus(ServiceStatus.OK);
    }

    public ReadServiceDTO[] get(String name) {
        return this.gateway.getServicesByNameAndStatus(name, ServiceStatus.OK);
    }

    public ReadServiceDTO getBalanced(String name) {
        ReadServiceDTO[] services = this.gateway.getServicesByNameAndStatus(name, ServiceStatus.OK);

        if (services.length > 0) {
            currentServiceLength.put(name, services.length);

            int currentIndex = lastServiceIndex.getOrDefault(name, 0);
            int currentLength = currentServiceLength.getOrDefault(name, services.length);

            currentIndex = (currentIndex + 1) % currentLength;
            
            if (currentIndex == Integer.MAX_VALUE) {
                currentIndex = 0;
            }

            lastServiceIndex.put(name, currentIndex);

            return services[currentIndex];
        }

        return null;
    }

}

