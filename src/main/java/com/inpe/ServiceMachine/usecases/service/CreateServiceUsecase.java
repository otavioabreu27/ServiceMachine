package com.inpe.ServiceMachine.usecases.service;

import com.inpe.ServiceMachine.adapters.dto.CreateServiceDTO;
import com.inpe.ServiceMachine.adapters.gateways.ServiceGateway;

public abstract class CreateServiceUsecase implements ServiceUsecase {
    private final ServiceGateway gateway;

    protected CreateServiceUsecase(ServiceGateway gateway) {
        this.gateway = gateway;
    }

    public void create(CreateServiceDTO service) {
        this.gateway.addService(service);
    }
}
