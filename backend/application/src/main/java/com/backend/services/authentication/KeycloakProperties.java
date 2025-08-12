package com.backend.services.authentication;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration properties for Keycloak integration.
 * 
 * This class maps application properties with the "keycloak" prefix to
 * provide centralized configuration for Keycloak authentication services.
 * It includes helper methods to construct commonly used Keycloak URLs.
 * 
 * @since 1.0
 * @author Backend Team
 */
@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "keycloak")
public class KeycloakProperties {
    private String baseUrl;
    private String realm;
    private String clientId;
    private String clientSecret;

    public String getTokenUrl() {
        return baseUrl + "/realms/" + realm + "/protocol/openid-connect/token";
    }

    public String getUserCreateUrl() {
        return baseUrl + "/admin/realms/" + realm + "/users";
    }
}
