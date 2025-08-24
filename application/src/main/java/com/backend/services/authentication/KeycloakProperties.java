package com.backend.services.authentication;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration properties for Keycloak integration.
 * 
 * This class maps application properties with the "keycloak" prefix to
 * provide centralized configuration for Keycloak authentication services.
 * It includes helper methods to construct commonly used Keycloak URLs.
 */
@Configuration
@ConfigurationProperties(prefix = "keycloak")
public record KeycloakProperties(
    String baseUrl,
    String realm,
    String clientId,
    String clientSecret) {

    public String tokenUrl() {
        return baseUrl + "/realms/" + realm + "/protocol/openid-connect/token";
    }

    public String userCreateUrl() {
        return baseUrl + "/admin/realms/" + realm + "/users";
    }
}
