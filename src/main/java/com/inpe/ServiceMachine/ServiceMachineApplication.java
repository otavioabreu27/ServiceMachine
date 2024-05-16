package com.inpe.ServiceMachine;

import com.inpe.ServiceMachine.adapters.dto.CreateServiceDTO;
import com.inpe.ServiceMachine.adapters.enums.ServiceStatus;
import com.inpe.ServiceMachine.adapters.gateways.ServiceGatewayRedis;
import com.inpe.ServiceMachine.drivers.repository.ServiceRepositoryRedis;
import com.inpe.ServiceMachine.usecases.service.CreateServiceUsecaseRedis;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ServiceMachineApplication {
	public static void main(String[] args) {
		SpringApplication.run(ServiceMachineApplication.class, args);
//		ServiceRepositoryRedis repository = new ServiceRepositoryRedis();
//		ServiceGatewayRedis gateway = new ServiceGatewayRedis(repository);
//		CreateServiceUsecaseRedis createUsecase = new CreateServiceUsecaseRedis(gateway);
//
//		CreateServiceDTO service = new CreateServiceDTO(
//				"Teste",
//				2222,
//				"testenome",
//				"123",
//				ServiceStatus.OK
//		);
//
//		createUsecase.create(service);
	}
}
