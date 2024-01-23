package com.plopez.diceroller.microservice.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class S5T2SpringBootDiceRollerMicroserviceGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(S5T2SpringBootDiceRollerMicroserviceGatewayApplication.class, args);
	}

}
