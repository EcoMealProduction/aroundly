package com.backend.infra.security.roles;

import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RoleMapperTest {

    @Test
    void testMapRolesToAuthorities() {
        final int ADMIN_ROLE_INDEX = 1;
        List<String> roles = List.of("BUSINESS", "ADMIN", "USER");
        List<GrantedAuthority> authorities = RoleMapper.mapRolesToAuthorities(roles);

        assertEquals(3, authorities.size());
        assertEquals("ROLE_BUSINESS", authorities.getFirst().getAuthority());
        assertEquals("ROLE_ADMIN", authorities.get(ADMIN_ROLE_INDEX).getAuthority());
        assertEquals("ROLE_USER", authorities.getLast().getAuthority());
    }
}
