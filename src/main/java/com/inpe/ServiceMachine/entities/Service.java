package com.inpe.ServiceMachine.entities;

import com.inpe.ServiceMachine.adapters.enums.ServiceStatus;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Service {
    private String hash;
    private String address;
    private int port;
    private String name;
    private String healthUrl;
    private ServiceStatus status;
    private LocalDateTime lastCheck;
}
