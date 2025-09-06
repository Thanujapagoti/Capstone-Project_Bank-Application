package com.bankapp.thanuja;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer

public class DiscoveryServerBankApplication {

	public static void main(String[] args) {
		SpringApplication.run(DiscoveryServerBankApplication.class, args);
	}

}
