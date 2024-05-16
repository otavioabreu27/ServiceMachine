package com.inpe.ServiceMachine.adapters.gateways;

import com.inpe.ServiceMachine.adapters.dto.CreateServiceDTO;
import com.inpe.ServiceMachine.adapters.dto.ReadServiceDTO;
import com.inpe.ServiceMachine.adapters.enums.ServiceStatus;
import com.inpe.ServiceMachine.adapters.operations.ServiceOperations;
import com.inpe.ServiceMachine.drivers.repository.ServiceRepository;

public abstract class ServiceGateway implements Gateway, ServiceOperations {
    protected ServiceRepository repository;

    public ServiceGateway(ServiceRepository repository) {
        this.repository = repository;
    }

    @Override
    public void addService(CreateServiceDTO service) {
        this.repository.addService(service);
    }

    @Override
    public ReadServiceDTO removeService(String serviceHash) {
        return this.repository.removeService(serviceHash);
    }

    @Override
    public ReadServiceDTO[] getServicesByName(String serviceName) {
        return this.repository.getServicesByName(serviceName);
    }

    @Override
    public ReadServiceDTO[] getServicesByStatus(ServiceStatus serviceStatus) {
        return this.repository.getServicesByStatus(serviceStatus);
    }

    @Override
    public ReadServiceDTO[] getServicesByNameAndStatus(String serviceName, ServiceStatus serviceStatus) {
        return this.repository.getServicesByNameAndStatus(serviceName, serviceStatus);
    }

    @Override
    public ReadServiceDTO[] getAllServices() {
        return this.repository.getAllServices();
    }

    @Override
    public void updateServiceStatus(String serviceHash, String serviceName, ServiceStatus newServiceStatus) {
        this.repository.updateServiceStatus(serviceHash, serviceName, newServiceStatus);
    }
}
