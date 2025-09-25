package com.backend.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Aroundly API",
                version = "1.0",
                description = """
                        # Aroundly City Safety API
                        
                        This API provides endpoints for managing city safety incidents and events.
                        
                        ## Authentication
                        The API uses JWT (JSON Web Token) authentication with Keycloak integration.
                        
                        ## Roles
                        - **USER**: Regular user access
                        - **BUSINESS**: Business account with additional event management capabilities
                        - **ADMIN**: Administrative access to all endpoints
                        
                        ## Security Endpoints
                        - `/public/**` - Public endpoints, no authentication required
                        - `/admin/**` - Admin role required
                        - `/api/v1/events/**` - Business role required
                        - `/api/v1/**` - Authentication required
                        """,
                contact = @Contact(
                        name = "Aroundly Team",
                        email = "team@aroundly.com"
                )
        ),
        servers = {
                @Server(url = "http://localhost:8080", description = "Development server"),
                @Server(url = "https://api.aroundly.com", description = "Production server")
        }
)
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer",
        description = "JWT token obtained from Keycloak authentication"
)
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new io.swagger.v3.oas.models.info.Info()
                        .title("Aroundly API")
                        .version("1.0")
                        .description("City safety incident and event management API")
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")))
                .addSecurityItem(new SecurityRequirement()
                        .addList("bearerAuth"));
    }

}
