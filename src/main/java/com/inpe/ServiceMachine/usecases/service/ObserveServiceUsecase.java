package com.inpe.ServiceMachine.usecases.service;

import com.inpe.ServiceMachine.adapters.dto.ReadServiceDTO;
import com.inpe.ServiceMachine.adapters.enums.ServiceStatus;
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

    public void restartCheck() {
         // Pickup monitored services after restart
        ReadServiceDTO[] services = this.repository.getServicesByStatus(ServiceStatus.OK);
        for (ReadServiceDTO service : services) {
            Thread thread = new Thread(
                    new ServiceObserver(
                        this.repository, service.getHealthUrl(), service.getHash(), service.getName()
                    )
            );
            thread.start();
        }
    }
}
