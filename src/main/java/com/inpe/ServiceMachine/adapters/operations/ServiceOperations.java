package com.inpe.ServiceMachine.adapters.operations;

import com.inpe.ServiceMachine.adapters.dto.CreateServiceDTO;
import com.inpe.ServiceMachine.adapters.dto.ReadServiceDTO;
import com.inpe.ServiceMachine.adapters.enums.ServiceStatus;

public interface ServiceOperations extends Operations {
    void addService(CreateServiceDTO service);
    ReadServiceDTO removeService(String serviceHash);
    ReadServiceDTO[] getServicesByName(String serviceName);
    ReadServiceDTO[] getServicesByStatus(ServiceStatus serviceStatus);
    ReadServiceDTO[] getServicesByNameAndStatus(String serviceName, ServiceStatus serviceStatus);
    ReadServiceDTO[] getAllServices();
    void updateServiceStatus(String serviceHash, String serviceName, ServiceStatus newServiceStatus);
}
