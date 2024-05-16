package com.inpe.ServiceMachine.drivers.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.inpe.ServiceMachine.adapters.dto.CreateServiceDTO;
import com.inpe.ServiceMachine.adapters.dto.ReadServiceDTO;
import com.inpe.ServiceMachine.adapters.enums.ServiceStatus;
import com.inpe.ServiceMachine.adapters.operations.ServiceOperations;

/**
 * Abstract class representing a service repository.
 * This class provides common functionality for service repositories.
 */
public abstract class ServiceRepository implements Repository, ServiceOperations {
    /**
     * Adds a new service to the repository.
     * Implementations of this method should handle the logic for adding a service.
     */
    public abstract void addService(CreateServiceDTO service);

    /**
     * Purges a service of the repository.
     * Implementations of this method should handle the logic for removing a service based on
     * the service hash.
     *
     * @param serviceHash unique identifier hash from service
     * @return the removed service
     */
    public abstract ReadServiceDTO removeService(String serviceHash);

    /**
     *
     *  Searches for services with the given name on the repository.
     *  Implementations of this method should handle the logic for finding services
     *  based on its name.
     *
     * @param serviceName the name that the logic should look for
     * @return an array of services that match that name
     */
    public abstract ReadServiceDTO[] getServicesByName(String serviceName);

    /**
     *
     *  Searches for services with the given status on the repository.
     *  Implementations of this method should handle the logic for finding services
     *  based on the service status.
     *
     * @param serviceStatus the status that the logic should look for
     * @return an array of services that match that status
     */
    public abstract ReadServiceDTO[] getServicesByStatus(ServiceStatus serviceStatus);

    /**
     *
     *  Searches for services with the given status and name on the repository.
     *  Implementations of this method should handle the logic for finding services
     *  based on the given service status and name.
     *
     * @param serviceName the name that the logic should look for
     * @param serviceStatus the status that the logic should look for
     * @return an array of services that matches the name and status
     */
    public abstract ReadServiceDTO[] getServicesByNameAndStatus(String serviceName, ServiceStatus serviceStatus);

    /**
     *
     *  Searches for all services
     *
     * @return a array with all services on the current repository
     */
    public abstract ReadServiceDTO[] getAllServices();
}
