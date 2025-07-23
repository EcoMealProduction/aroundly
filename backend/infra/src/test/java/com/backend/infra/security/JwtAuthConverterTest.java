package com.backend.infra.security;

import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class JwtAuthConverterTest {

    @Test
    void testJwtExtractionFromSecurityContext() {
        Jwt jwt = new Jwt(
                "bruh_fake_token?",
                Instant.now(),
                Instant.now().plusSeconds(3000),
                Map.of("alg", "none"),
                Map.of(
                        "sub", "1234567681",
                        "preferred_username", "Vaniusha",
                        "realm_access", Map.of("roles", List.of("user"))
                )
        );

        JwtAuthConverter converter = new JwtAuthConverter();
        JwtAuthenticationToken token = (JwtAuthenticationToken) converter.convert(jwt);

        assertNotNull(token);
        Collection<GrantedAuthority> authorities = token.getAuthorities();
        assertTrue(authorities.stream().anyMatch(role -> role.getAuthority().equals("ROLE_user")));
    }
}
