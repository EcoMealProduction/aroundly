package com.backend.domain.user;

import lombok.Builder;
import lombok.NonNull;

/**
 * Represents a client (active user) in the system, linked to a Keycloak account.
 * Contains user profile information and device tokens for notifications.
 */
@Builder(toBuilder = true)
public record Client(
        @NonNull String keycloakId,
        @NonNull String username,
        String avatarUrl,
        String fcmToken
) {

    /**
     * Constructs a {@code Client} instance with validation.
     *
     * @throws IllegalArgumentException if:
     *   @param username is shorter than 3 characters
     *   @param keycloakId is empty or null
     */
    public Client {
        if (username.length() < 3)
            throw new IllegalArgumentException("Username must be at least 3 characters long.");
        
        if (keycloakId == null || keycloakId.trim().isEmpty())
            throw new IllegalArgumentException("Keycloak ID cannot be empty.");
    }
}