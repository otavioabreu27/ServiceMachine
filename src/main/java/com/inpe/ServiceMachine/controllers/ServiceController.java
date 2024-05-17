package com.inpe.ServiceMachine.controllers;

import com.inpe.ServiceMachine.adapters.dto.CreateServiceDTO;
import com.inpe.ServiceMachine.adapters.dto.ReadServiceDTO;
import com.inpe.ServiceMachine.drivers.generators.GeneratorHash;
import com.inpe.ServiceMachine.usecases.service.CreateServiceUsecaseRedis;
import com.inpe.ServiceMachine.usecases.service.GetActiveServicesUsecaseRedis;
import com.inpe.ServiceMachine.usecases.service.ObserverServiceUsecaseRedis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ServiceController {
    private final CreateServiceUsecaseRedis createUsecase;
    private final ObserverServiceUsecaseRedis observerUsecase;
    private final GetActiveServicesUsecaseRedis getActiveUsecase;

    @Autowired
    public ServiceController(
            CreateServiceUsecaseRedis createUsecase,
            ObserverServiceUsecaseRedis observerUsecase,
            GetActiveServicesUsecaseRedis getActiveUsecase
    ) {
        this.createUsecase = createUsecase;
        this.observerUsecase = observerUsecase;
        this.getActiveUsecase = getActiveUsecase;

        // Check for old services that were being monitored
        this.observerUsecase.restartCheck();
    }

    @PostMapping("/register")
    public ResponseEntity<String> createService(@RequestBody CreateServiceDTO service) {
        String hash = GeneratorHash.generate(service.toString());
        this.createUsecase.create(service);
        this.observerUsecase.observe(service.getHealthUrl(), hash, service.getName());
        return ResponseEntity.status(201).body(hash);
    }

    @GetMapping("/services/active")
    public ResponseEntity<ReadServiceDTO[]> getActiveServices(
            @RequestParam(required = false) String name
    ) {
        if (name == null) {
            return ResponseEntity.status(200).body(this.getActiveUsecase.get());
        } else {
            return ResponseEntity.status(200).body(this.getActiveUsecase.get(name));
        }
    }

    @GetMapping("/services/active/balanced")
    public ResponseEntity<ReadServiceDTO> getActiveServiceBalanced(
            @RequestParam(required = true) String name
    ) {
        return ResponseEntity.status(200).body(this.getActiveUsecase.getBalanced(name));
    }
}
