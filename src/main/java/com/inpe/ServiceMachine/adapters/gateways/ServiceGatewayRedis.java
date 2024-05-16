package com.inpe.ServiceMachine.adapters.gateways;

import com.inpe.ServiceMachine.adapters.enums.ServiceStatus;
import com.inpe.ServiceMachine.drivers.repository.ServiceRepository;
import com.inpe.ServiceMachine.drivers.repository.ServiceRepositoryRedis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ServiceGatewayRedis extends ServiceGateway {
    @Autowired
    public ServiceGatewayRedis(ServiceRepositoryRedis repository) {
        super(repository);
    }
}
