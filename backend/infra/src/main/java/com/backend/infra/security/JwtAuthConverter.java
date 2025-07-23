package com.backend.infra.security;


import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Converts a JWT token into an {@link AbstractAuthenticationToken} that Spring Security can use
 * for authentication and authorization.
 *
 * This implementation specifically extracts user roles from the "realm_access" claim (as used by Keycloak)
 * and maps them into {@link SimpleGrantedAuthority} objects.
 */
public class JwtAuthConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    /**
     * Converts the given {@link Jwt} into a {@link JwtAuthenticationToken}, mapping roles from the
     * "realm_access" claim into Spring Security authorities.
     *
     * @param source the decoded JWT token
     * @return an authenticated {@link JwtAuthenticationToken} containing granted authorities
     */
    @Override
    public AbstractAuthenticationToken convert(Jwt source) {
        /**
         * @realm_access: a Keycloak-specific standard claim. This claim is included
         *                in the access token automatically by Keycloak when the user
         *                has realm-level roles assigned.
         */
        Map<String, Object> realmAccess = source.getClaim("realm_access");
        List<String> roles = (List<String>) realmAccess.getOrDefault("roles", List.of());

        Collection<SimpleGrantedAuthority> authorities = roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .collect(Collectors.toSet());

        return new JwtAuthenticationToken(source, authorities);
    }
}
