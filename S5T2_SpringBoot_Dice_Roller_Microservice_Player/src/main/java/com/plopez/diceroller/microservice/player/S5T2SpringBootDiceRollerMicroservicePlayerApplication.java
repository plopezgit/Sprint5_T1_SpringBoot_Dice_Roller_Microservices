package com.plopez.diceroller.microservice.player;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
@OpenAPIDefinition(info = @Info(title = "Player microservice", description = "Process the information related to " +
		"the Dice roller Game Players; It is able to connect to Game microservice so a specific player is able to play games, " +
		"and delete all the games of a specific player.", version = "1.0.0"))
public class S5T2SpringBootDiceRollerMicroservicePlayerApplication {

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	public static void main(String[] args) {
		SpringApplication.run(S5T2SpringBootDiceRollerMicroservicePlayerApplication.class, args);
	}

}
