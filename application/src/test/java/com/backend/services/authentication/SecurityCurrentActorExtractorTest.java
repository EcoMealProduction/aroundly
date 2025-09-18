package com.backend.services.authentication;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import com.backend.domain.actor.ActorId;
import java.time.Instant;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

@ExtendWith(MockitoExtension.class)
public class SecurityCurrentActorExtractorTest {

  private SecurityCurrentActorExtractor actorExtractor;
  @Mock private Authentication authentication;
  @Mock private SecurityContext context;

  @BeforeEach
  void setup() {
    actorExtractor = new SecurityCurrentActorExtractor();
    SecurityContextHolder.setContext(context);
    when(context.getAuthentication()).thenReturn(authentication);
  }

  @AfterEach
  void tearDown() {
    SecurityContextHolder.clearContext();
  }

  @Test
  void testExtractId() {
    String expectedActorId = "abc-123";
    Jwt mockJwt = createMockJwt(Map.of("sub", expectedActorId));
    when(authentication.getPrincipal()).thenReturn(mockJwt);

    ActorId actorId = actorExtractor.extractId();

    assertNotNull(actorId);
    assertEquals(expectedActorId, actorId.id());
  }

  @Test
  void testExtractRoles() {
    String expectedUsername = "vanea";

    Jwt mockJwt = createMockJwt(Map.of(
        "sub", "user-123",
        "username", expectedUsername
    ));

    when(authentication.getPrincipal()).thenReturn(mockJwt);
    Optional<String> username = actorExtractor.extractUsername();

    assertTrue(username.isPresent());
    assertEquals(expectedUsername, username.get());
  }

  private Jwt createMockJwt(Map<String, Object> claims) {
    Jwt.Builder jwtBuilder = Jwt.withTokenValue("mock-token")
        .header("alg", "RS256")
        .header("typ", "JWT")
        .issuedAt(Instant.now())
        .expiresAt(Instant.now().plusSeconds(3600));

    claims.forEach(jwtBuilder::claim);

    return jwtBuilder.build();
  }

}
