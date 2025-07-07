package com.backend.port.in;

import com.backend.user.User;

import java.util.Optional;

/**
 * Defines use cases related to user-specific actions.
 */
public interface UserUseCase {
    /**
     * Sets up or updates the visibility range for the user.
     * This could represent how far (e.g., in meters or kilometers) the user's activities,
     * location, or shared content are visible to others.
     *
     * @param userId the id of existing user
     * @param range The visibility range to set for the user.
     * @return The updated User object reflecting the new visibility range.
     */
    User setUpVisibilityRange(long userId, int range);

    /**
     * Retrieves a user by their unique numeric identifier.
     *
     * @param userId The unique ID of the user to find.
     * @return The User object if found, or null if not found.
     */
    Optional<User> findById(int userId);

    /**
     * Retrieves a user by their associated Keycloak identity.
     *
     * @param keycloakId The Keycloak ID associated with the user.
     * @return The User object if found, or null if not found.
     */
    Optional<User> findByKeycloakId(String keycloakId);
}
