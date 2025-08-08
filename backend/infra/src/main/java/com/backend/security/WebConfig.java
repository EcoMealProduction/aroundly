package com.backend.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Global web configuration class for customizing MVC-related behavior.
 *
 * Specifically, this class configures Cross-Origin Resource Sharing (CORS) settings
 * for the application using Spring's {@link WebMvcConfigurer}. These settings apply to
 * all HTTP requests matching the pattern "/api/v1/**".
 *
 * CORS is required when a frontend (e.g., React, Angular) served from a different origin
 * (such as http://localhost:3000) attempts to make requests to this backend.
 *
 * Configuration highlights:
 *     - Allows requests from origin: http://localhost:3000
 *     - Permits methods: GET, POST, PUT, DELETE, OPTIONS
 *     - Allows credentials (cookies, Authorization headers, etc.)
 *     - Allows specific request headers and exposes response headers
 *     - Caches preflight responses for 1 hour
 *
 * Note: This configuration must be paired with a call to {@code http.cors()} in the
 * Spring Security filter chain to take full effect.
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/v1/**")
                .allowedOrigins("http://localhost:3000")
                .allowedHeaders("Authorization", "Content-Type", "X-Requested-With")
                .exposedHeaders("Authorization", "X-Custom-Header")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowCredentials(true)
                .maxAge(3600);
    }
}
