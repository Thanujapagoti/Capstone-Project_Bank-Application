package com.bank.notification;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer

public class NotificationServiceBankApplication {

	public static void main(String[] args) {
		SpringApplication.run(NotificationServiceBankApplication.class, args);
	}

}
