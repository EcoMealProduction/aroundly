package com.backend.infra.security.roles;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Utility class for converting raw role names (e.g., "admin", "user")
 * into Spring Security's GrantedAuthority format.
 *
 * Spring Security expects roles to be prefixed with "ROLE_" when using
 * expressions like @PreAuthorize("hasRole('ADMIN')").
 *
 * This class helps transform a list of raw role strings into a list
 * of GrantedAuthority objects with the proper "ROLE_" prefix.
 */
public class RoleMapper {

    /**
     * Converts a list of raw role names into a list of Spring Security
     * GrantedAuthority objects, each prefixed with "ROLE_".
     *
     * Example:
     *   Input: ["ADMIN", "BUSINESS"]
     *   Output: [ROLE_ADMIN, ROLE_BUSINESS]
     *
     * @param roles a list of raw role names (e.g., from a JWT token)
     * @return a list of GrantedAuthority objects with "ROLE_" prefix
     */
    public static List<GrantedAuthority> mapRolesToAuthorities(List<String> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.toUpperCase()))
                .collect(Collectors.toList());
    }
}
