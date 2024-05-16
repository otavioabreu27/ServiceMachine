package com.inpe.ServiceMachine.usecases.service;

import com.inpe.ServiceMachine.drivers.repository.ServiceRepositoryRedis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ObserverServiceUsecaseRedis extends ObserveServiceUsecase {
    @Autowired
    protected ObserverServiceUsecaseRedis(ServiceRepositoryRedis repository) {
        super(repository);
    }
}
