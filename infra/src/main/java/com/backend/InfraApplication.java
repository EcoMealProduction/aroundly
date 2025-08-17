package com.backend;

import com.backend.services.authentication.KeycloakProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(KeycloakProperties.class)
public class InfraApplication {
    public static void main(String[] args) {
        SpringApplication.run(InfraApplication.class, args);
    }
}
