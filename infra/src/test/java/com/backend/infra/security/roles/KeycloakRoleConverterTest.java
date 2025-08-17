package com.backend.infra.security.roles;

import com.backend.security.KeycloakRoleConverter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class KeycloakRoleConverterTest {

    @Test
    public void testRoleConverter() {
        Map<String, List<String>> clientRoles = Map.of(
                "roles", List.of("ADMIN", "BUSINESS", "USER"));

        Map<String, Object> resourceAccess = Map.of("aroundly", clientRoles);

        Map<String, Object> claims = Map.of(
                "resource_access", resourceAccess,
                "sub", "test_user"
        );

        Jwt jwt = Jwt.withTokenValue("fake-token")
                .header("alg", "none")
                .claims(claim -> claim.putAll(claims))
                .issuedAt(Instant.now())
                .expiresAt(Instant.now().plusSeconds(10))
                .build();

        KeycloakRoleConverter converter = new KeycloakRoleConverter();
        Collection<GrantedAuthority> authorities = converter.convert(jwt);

        Assertions.assertNotNull(authorities);
        assertEquals(3, authorities.size());
        assertTrue(authorities.stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_BUSINESS")));
        assertTrue(authorities.stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN")));
        assertTrue(authorities.stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_USER")));
    }
}
