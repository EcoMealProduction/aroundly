package com.backend.infra.security;

import com.backend.security.JwtAuthConverterConfig;
import com.backend.security.KeycloakRoleConverter;
import com.backend.security.SpringSecurity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@WebMvcTest(controllers = MockController.class)
@Import({SpringSecurity.class, JwtAuthConverterConfig.class})
class SpringSecurityTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private KeycloakRoleConverter keycloakRoleConverter;

    @Autowired
    private JwtAuthConverterConfig jwtAuthConverterConfig;

    @BeforeEach
    void setup() {
        when(keycloakRoleConverter.convert(any())).thenAnswer(invocationOnMock -> {
            Jwt jwt = invocationOnMock.getArgument(0);
            List<String> roles = ((Map<String, List<String>>) jwt.getClaim("realm_access")).get("roles");
            return roles.stream()
                    .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                    .collect(Collectors.toList());
        });
    }

    @Test
    void testAccessEventsWithBusinessRole() throws Exception {
        mockMvc.perform(get("/api/v1/events")
                        .with(authentication(jwtAuthTokenWithRoles("BUSINESS"))))
                .andExpect(status().isOk());
    }

    @Test
    void testAccessTextWithAdminRole() throws Exception {
        mockMvc.perform(get("/admin/text")
                        .with(authentication(jwtAuthTokenWithRoles("ADMIN"))))
                .andExpect(status().isOk());
    }

    private Jwt createMockTokenForUserRole(String... roles) {
        return Jwt.withTokenValue("fake-token")
                .header("alg", "none")
                .claim("resource_access", Map.of(
                        "aroundly", Map.of("roles", List.of(roles))))
                .issuedAt(Instant.now())
                .expiresAt(Instant.now().plusSeconds(10))
                .build();
    }

    private JwtAuthenticationToken jwtAuthTokenWithRoles(String... roles) {
        Jwt jwt = createMockTokenForUserRole(roles);
        List<GrantedAuthority> authorities = Arrays.stream(roles)
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .collect(Collectors.toList());

        return new JwtAuthenticationToken(jwt, authorities);
    }
}
