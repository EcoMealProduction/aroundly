package com.backend.domain.happening.metadata;

import com.backend.domain.happening.old.OldHappening;
import com.backend.domain.happening.old.OldIncident;
import com.backend.domain.media.Media;
import com.backend.domain.old.OldLocation;
import com.backend.domain.actor.Actor;
import java.util.Set;
import lombok.Builder;
import lombok.NonNull;

import java.time.LocalDateTime;

/**
 * Metadata specific to an {@link OldIncident}, including author, location,
 * creation timestamp, and expiration deadline.
 *
 * Implements the {@link Metadata} mixin interface used across different {@link OldHappening} types.
 */
@Builder(toBuilder = true)
public record IncidentMetadata(
        Actor actor,
        @NonNull OldLocation oldLocation,
        Set<Media> media,
        LocalDateTime createdAt,
        LocalDateTime expirationTime) implements Metadata {

    /**
     * Constructs an {@code IncidentMetadata} instance with defaulting and validation.
     * - Sets {@code createdAt} to {@code now()} if null.
     * - Sets {@code expirationTime} to 30 minutes after {@code createdAt} if null.
     * - Validates that {@code expirationTime} is not before {@code createdAt} or the current time.
     *
     * @throws IllegalArgumentException if:
     *   {@code expirationTime} is before {@code createdAt}
     *   {@code expirationTime} is in the past
     */
    public IncidentMetadata {
        if (createdAt == null)
            createdAt = LocalDateTime.now();

        if (expirationTime == null)
            expirationTime = createdAt.plusMinutes(30);

        if (expirationTime.isBefore(createdAt))
            throw new IllegalArgumentException("Expiration time cannot be before createdAt.");

        if (expirationTime.isBefore(LocalDateTime.now()))
            throw new IllegalArgumentException("Expiration time cannot be before now.");
    }

    /**
     * Checks if the incident has expired based on the current time.
     *
     * @return {@code true} if the expiration time is in the past.
     */
    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expirationTime);
    }

    @Override
    public OldLocation location() {
        return null;
    }
}
