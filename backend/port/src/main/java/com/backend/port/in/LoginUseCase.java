package com.backend.port.in;

/**
 * Defines use cases for user authentication operations.
 */
public interface LoginUseCase {

    /**
     * Authenticates a user with username/email and password.
     *
     * @param usernameOrEmail The username or email of the user.
     * @param password The password of the user.
     * @return Authentication response containing access token and user information.
     * @throws IllegalArgumentException if credentials are invalid.
     * @throws IllegalStateException if authentication fails due to server error.
     */
    LoginResponse authenticateUser(String usernameOrEmail, String password);

    /**
     * Represents the response from a successful login operation.
     */
    record LoginResponse(
            String accessToken,
            String tokenType,
            long expiresIn,
            String refreshToken,
            String username,
            String email
    ) {}
}