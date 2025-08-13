package com.backend.domain.user;

import io.micrometer.common.lang.NonNull;
import java.util.Set;

/**
 * Domain actor used to carry the caller’s identity and permissions into the core.
 * Immutable and value-based (record). Contains only domain-relevant data; no
 * authentication or profile concerns. Typically built in the application layer
 * from security claims and passed to aggregates/services for authorization checks.
 *
 * @param id   non-null ActorId uniquely identifying the actor in the domain
 * @param role non-null Role representing the actor’s domain capabilities
 */
public record Actor(
    @NonNull ActorId id,
    @NonNull String username,
    @NonNull Set<Role> role) {
}
