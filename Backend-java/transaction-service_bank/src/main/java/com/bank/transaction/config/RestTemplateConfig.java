package com.bank.transaction.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

// Use @LoadBalanced so Eureka service names can be resolved (e.g. http://account-service)
@Bean
@LoadBalanced
public RestTemplate restTemplate() {
return new RestTemplate();
}
}