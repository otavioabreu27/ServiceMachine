package com.inpe.ServiceMachine.usecases.service;

import com.inpe.ServiceMachine.adapters.observer.ServiceObserver;
import com.inpe.ServiceMachine.drivers.repository.ServiceRepository;

public abstract class ObserveServiceUsecase implements ServiceUsecase{
    private final ServiceRepository repository;

     protected ObserveServiceUsecase(ServiceRepository repository) {
        this.repository = repository;
    }

    public void observe(String healthUrl, String hash, String name) {
        Thread thread = new Thread(new ServiceObserver(this.repository, healthUrl, hash, name));
        thread.start();
    }
}
