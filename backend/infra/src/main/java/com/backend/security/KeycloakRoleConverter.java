package com.backend.security;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Custom converter that extracts client-specific roles from a Keycloak JWT
 * and maps them into Spring Security GrantedAuthority objects.
 *
 * This converter specifically looks into the `resource_access` claim of the JWT,
 * which contains role information for client-level roles assigned to the user.
 *
 * For example, if the token contains:
 * {
 *   "resource_access": {
 *     "aroundly": {
 *       "roles": ["BUSINESS", "ADMIN"]
 *     }
 *   }
 * }
 *
 * It will convert those roles into:
 *   - ROLE_BUSINESS
 *   - ROLE_ADMIN
 *
 * These are then used by Spring Security to control access (e.g., @PreAuthorize("hasRole('ADMIN')")).
 *
 * Note:
 * - This class is specific to client roles (not realm roles).
 * - The client ID is hardcoded as "aroundly" – modify if needed for other clients or make it configurable.
 */
public class KeycloakRoleConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

    private static final String CLIENT_ID = "aroundly";
    private final static Logger LOGGER = Logger.getLogger(KeycloakRoleConverter.class.getName());

    @Override
    public Collection<GrantedAuthority> convert(Jwt jwt) {
        Map<String, Object> resourceAccess = jwt.getClaim("resource_access");

        if (resourceAccess == null || !resourceAccess.containsKey(CLIENT_ID)) {
            LOGGER.config("Resource Access is null or it doesn't contain the client 'aroundly'...");
            return Collections.emptyList();
        }

        Map<String, Object> clientAccess = (Map<String, Object>) resourceAccess.get(CLIENT_ID);
        List<String> roles = (List<String>) clientAccess.get("roles");

        if (roles == null) {
            LOGGER.config("No roles found...");
            return Collections.emptyList();
        }

        return roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.toUpperCase()))
                .collect(Collectors.toList());
    }
}
