package com.backend.port.in;

import com.backend.user.User;

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
}
