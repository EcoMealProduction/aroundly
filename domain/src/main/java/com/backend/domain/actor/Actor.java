package com.backend.domain.actor;

import io.micrometer.common.lang.NonNull;
import java.util.Set;

/**
 * Represents an actor in the system with an identifier,
 * a username, and one or more assigned roles.
 *
 * @param id       the unique identifier of the actor
 * @param username the username of the actor
 * @param role     the roles assigned to the actor
 */
public record Actor(
    @NonNull ActorId id,
    @NonNull String username,
    @NonNull Set<Role> role) {
}
