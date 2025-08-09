package com.backend.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;

/**
 * Spring configuration class that integrates a custom JWT role converter
 * (`KeycloakRoleConverter`) into the authentication process.
 *
 * This converter transforms a raw Keycloak `Jwt` token into a
 * `JwtAuthenticationToken`, enriched with authorities (roles) extracted
 * from the token's `resource_access` claim using the Keycloak-specific logic.
 *
 * Purpose:
 * - Plug the custom role extraction logic into Spring Security.
 * - Ensure that client-specific roles from Keycloak are correctly mapped
 *   to Spring's GrantedAuthority system.
 *
 * This class is typically registered as a bean and used within the security
 * configuration to override the default authority mapping behavior.
 */
@Configuration
public class JwtAuthConverterConfig {

    /**
     * Converts the incoming Jwt token into a JwtAuthenticationToken using a custom
     * GrantedAuthoritiesConverter (`KeycloakRoleConverter`) to extract roles.
     *
     * @return a Spring Security JwtAuthenticationToken with extracted authorities
     */
    @Bean
    public Converter<Jwt, ? extends AbstractAuthenticationToken> jwtAuthConverter() {
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(new KeycloakRoleConverter());
        return jwtAuthenticationConverter;
    }
}
