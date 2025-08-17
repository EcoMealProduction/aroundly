package com.backend.security;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    @Override
    public Collection<GrantedAuthority> convert(Jwt jwt) {
        Collection<GrantedAuthority> authorities = extractAuthorities(jwt);
        return new UsernamePasswordAuthenticationToken(jwt.getSubject(), "n/a", authorities).getAuthorities();
    }

    private Collection<GrantedAuthority> extractAuthorities(Jwt jwt) {
        List<String> clientRoles = Optional.ofNullable(getMap(jwt.getClaim("resource_access")))
                .map(m -> getMap(m.get(CLIENT_ID)))
                .map(m -> getList(m.get("roles")))
                .orElse(Collections.emptyList());

        List<String> realmRoles = Optional.ofNullable(getMap(jwt.getClaim("realm_access")))
                .map(m -> getList(m.get("roles")))
                .orElse(Collections.emptyList());

        return Stream.concat(clientRoles.stream(), realmRoles.stream())
                .filter(Objects::nonNull)
                .distinct()
                .map(r -> r.startsWith("ROLE_") ? r : "ROLE_" + r)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toUnmodifiableList());
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> getMap(Object obj) {
        return (obj instanceof Map) ? (Map<String, Object>) obj : null;
    }

    @SuppressWarnings("unchecked")
    private List<String> getList(Object obj) {
        return (obj instanceof List) ? (List<String>) obj : null;
    }
}
