package com.backend.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
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
     * @param jwtAuthConverterConfig custom {@link JwtAuthConverterConfig} role converter into the authentication process
     * @return the configured {@link SecurityFilterChain}
     * @throws Exception in case of configuration errors
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, JwtAuthConverterConfig jwtAuthConverterConfig) throws Exception {
        httpSecurity
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/api/v1/events/**", "/api/v1/events").hasRole("BUSINESS")
                        .anyRequest().authenticated())
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthConverterConfig.jwtAuthConverter())));

        return httpSecurity.build();
    }
}
