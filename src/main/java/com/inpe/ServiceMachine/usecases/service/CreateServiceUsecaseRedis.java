package com.inpe.ServiceMachine.usecases.service;

import com.inpe.ServiceMachine.adapters.gateways.ServiceGatewayRedis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CreateServiceUsecaseRedis extends CreateServiceUsecase {
    @Autowired
    public CreateServiceUsecaseRedis(ServiceGatewayRedis gateway) {
        super(gateway);
    }
}
