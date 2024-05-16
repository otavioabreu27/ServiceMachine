package com.inpe.ServiceMachine.adapters.dto;

import com.inpe.ServiceMachine.adapters.enums.ServiceStatus;

public class CreateServiceDTO {
    private String address;
    private int port;
    private String name;
    private String healthUrl;

    public CreateServiceDTO(String address, int port, String name, String healthUrl) {
        this.address = address;
        this.port = port;
        this.name = name;
        this.healthUrl = healthUrl;
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
}
