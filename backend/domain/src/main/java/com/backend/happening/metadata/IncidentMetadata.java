package com.backend.happening.metadata;

import com.backend.shared.Location;
import lombok.Builder;
import lombok.NonNull;

import java.time.LocalDateTime;

@Builder(toBuilder = true)
public record IncidentMetadata(
        @NonNull String authorUsername,
        @NonNull Location location,
        LocalDateTime createdAt,
        LocalDateTime expirationTime) implements Metadata {

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
}
