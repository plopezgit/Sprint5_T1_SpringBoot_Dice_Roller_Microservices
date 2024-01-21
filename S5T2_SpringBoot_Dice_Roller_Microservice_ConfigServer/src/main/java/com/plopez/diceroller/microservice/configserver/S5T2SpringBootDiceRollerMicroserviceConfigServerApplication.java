package com.plopez.diceroller.microservice.configserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class S5T2SpringBootDiceRollerMicroserviceConfigServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(S5T2SpringBootDiceRollerMicroserviceConfigServerApplication.class, args);
	}

}
