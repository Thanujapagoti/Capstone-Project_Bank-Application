package com.bank.gateway.security;



import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable) // disable CSRF
                .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable) // disable basic auth
                .formLogin(ServerHttpSecurity.FormLoginSpec::disable) // disable login form
                .authorizeExchange(exchange -> exchange
                        .pathMatchers("/auth/**", "/actuator/**").permitAll() // open endpoints
                        .anyExchange().permitAll() // JWTAuthFilter will validate
                )
                .build();
    }
}
