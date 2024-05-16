package com.inpe.ServiceMachine.controllers;

import com.inpe.ServiceMachine.adapters.dto.CreateServiceDTO;
import com.inpe.ServiceMachine.adapters.observer.ServiceObserver;
import com.inpe.ServiceMachine.drivers.generators.GeneratorHash;
import com.inpe.ServiceMachine.usecases.service.CreateServiceUsecaseRedis;
import com.inpe.ServiceMachine.usecases.service.ObserveServiceUsecase;
import com.inpe.ServiceMachine.usecases.service.ObserverServiceUsecaseRedis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ServiceController {
    private final CreateServiceUsecaseRedis createUsecase;
    private final ObserverServiceUsecaseRedis observerUsecase;

    @Autowired
    public ServiceController(
            CreateServiceUsecaseRedis createUsecase,
            ObserverServiceUsecaseRedis observerUsecase
    ) {
        this.createUsecase = createUsecase;
        this.observerUsecase = observerUsecase;
    }

    @PostMapping("/service")
    public ResponseEntity<String> createService(@RequestBody CreateServiceDTO service) {
        String hash = GeneratorHash.generate(service.toString());
        this.createUsecase.create(service);
        this.observerUsecase.observe(service.getHealthUrl(), hash, service.getName());
        return ResponseEntity.status(201).body("Serviço criado");
    }
}
