package com.backend.services.authentication;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

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
