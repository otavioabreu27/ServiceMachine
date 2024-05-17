package com.inpe.ServiceMachine.adapters.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.inpe.ServiceMachine.adapters.enums.ServiceStatus;

public class ReadServiceDTO {
    private String hash;
    private String address;
    private int port;
    private String name;
    private String healthUrl;
    private ServiceStatus status;

    public ReadServiceDTO() {
    }

    @JsonCreator
    public ReadServiceDTO(
            @JsonProperty("hash") String hash,
            @JsonProperty("address") String address,
            @JsonProperty("port") int port,
            @JsonProperty("name") String name,
            @JsonProperty("healthUrl") String healthUrl,
            @JsonProperty("status") ServiceStatus status) {
        this.hash = hash;
        this.address = address;
        this.port = port;
        this.name = name;
        this.healthUrl = healthUrl;
        this.status = status;
    }

    // Getters and setters
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
