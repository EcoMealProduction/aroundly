package com.backend.infra.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Spring Security configuration class that defines security rules and JWT-based authentication.
 */
@Configuration
public class SpringSecurity {

    /**
     * Configures the security filter chain for the application.
     *
     * - Allows unauthenticated access to the "/public" endpoint.
     * - Requires authentication for all other requests.
     * - Uses JWT-based authentication via OAuth2 resource server.
     * - Applies a custom JWT converter to extract user roles.
     *
     * @param httpSecurity the {@link HttpSecurity} to configure
     * @return the configured {@link SecurityFilterChain}
     * @throws Exception in case of configuration errors
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/public").permitAll()
                        .anyRequest().authenticated())
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(
                        jwt -> jwt.jwtAuthenticationConverter(new JwtAuthConverter())));

        return httpSecurity.build();
    }
}
