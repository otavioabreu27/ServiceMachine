package com.inpe.ServiceMachine.adapters.dto;

import com.inpe.ServiceMachine.adapters.enums.ServiceStatus;
import lombok.Data;

public class ReadServiceDTO {
    private String hash;
    private String address;
    private int port;
    private String name;
    private String healthUrl;
    private ServiceStatus status;

    public ReadServiceDTO(String hash, String address, int port, String name, String healthUrl, ServiceStatus status) {
        this.hash = hash;
        this.address = address;
        this.port = port;
        this.name = name;
        this.healthUrl = healthUrl;
        this.status = status;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHealthUrl() {
        return healthUrl;
    }

    public void setHealthUrl(String healthUrl) {
        this.healthUrl = healthUrl;
    }

    public ServiceStatus getStatus() {
        return status;
    }

    public void setStatus(ServiceStatus status) {
        this.status = status;
    }
}
