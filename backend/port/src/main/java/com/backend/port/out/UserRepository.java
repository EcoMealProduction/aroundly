package com.backend.port.out;

import com.backend.user.User;

import java.util.Optional;

/**
 * Repository interface for accessing and managing User entities in the data store.
 */
public interface UserRepository {

    /**
     * Saves a new user or updates an existing user.
     *
     * @param client The User object to be saved or updated.
     * @return The saved or updated User object (with assigned ID if new).
     */
    User save(User client);

    /**
     * Finds a user by their unique numeric identifier.
     *
     * @param userId The unique ID of the user to retrieve.
     * @return An Optional containing the User if found, or empty if not found.
     */
    Optional<User> findById(int userId);

    /**
     * Finds a user by their associated Keycloak ID.
     *
     * @param keycloakId The Keycloak ID associated with the user.
     * @return An Optional containing the User if found, or empty if not found.
     */
    Optional<User> findByKeycloakId(String keycloakId);

    /**
     * Deletes a user by their unique identifier.
     *
     * @param id The unique ID of the user to delete.
     */
    void deleteById(int id);
}
