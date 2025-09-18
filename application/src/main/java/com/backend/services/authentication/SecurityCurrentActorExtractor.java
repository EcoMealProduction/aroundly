package com.backend.services.authentication;

import com.backend.domain.actor.ActorId;
import com.backend.domain.actor.Role;
import com.backend.port.inbound.ActorUseCase;
import java.util.Optional;
import java.util.Set;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component
@RequestScope
public class SecurityCurrentActorExtractor implements ActorUseCase {

  /**
   * Returns the current actor's identifier from the JWT "sub" (subject) claim.
   *
   * @return the current actor id
   * @throws IllegalStateException if no authenticated JWT is present
   */
  @Override
  public ActorId extractId() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    if (auth == null || !(auth.getPrincipal() instanceof org.springframework.security.oauth2.jwt.Jwt jwt))
      throw new IllegalStateException("No authenticated user");
    return new ActorId(jwt.getSubject());
  }

  /**
   * Extracts the set of roles/authorities for the current actor.
   *
   * This implementation maps Spring Security GrantedAuthority values
   * into Role enum by stripping common prefixes like "ROLE_" and "SCOPE_".
   * Customize this mapping to match your Keycloak realm roles as needed.
   *
   * @return the actor's roles, possibly empty
   */
  @Override
  public Set<Role> extractRoles() {
    return Set.of();
  }

  /**
   * Extracts a username from common JWT claims.
   *
   * Tries {@code preferred_username}, then {@code username}, then {@code email}.
   *
   * @return an Optional username if present
   */
  @Override
  public Optional<String> extractUsername() {
    Jwt jwt = getJwtOrThrow();
    String username = jwt.getClaimAsString("username");
    if (username != null && !username.isBlank()) {
      return Optional.of(username);
    }
    String email = jwt.getClaimAsString("email");
    return Optional.ofNullable((email != null && !email.isBlank()) ? email : null);
  }

  @Override
  public Optional<String> extractAvatarUrl() {
    return Optional.empty();
  }

  /**
   * Fetches the current JWT from the security context or throws if unavailable.
   *
   * @return the current {@link Jwt}
   * @throws IllegalStateException if no authenticated JWT is present
   */
  private Jwt getJwtOrThrow() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    if (auth == null || !(auth.getPrincipal() instanceof Jwt jwt)) {
      throw new IllegalStateException("No authenticated user");
    }
    return jwt;
  }
}
