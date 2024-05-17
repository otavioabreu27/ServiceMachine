package com.inpe.ServiceMachine.usecases.service;

import com.inpe.ServiceMachine.adapters.gateways.ServiceGatewayRedis;
import org.springframework.stereotype.Component;

@Component
public class GetActiveServicesUsecaseRedis extends GetActiveServicesUsecase {
    public GetActiveServicesUsecaseRedis(ServiceGatewayRedis gateway) {
        super(gateway);
    }
}
