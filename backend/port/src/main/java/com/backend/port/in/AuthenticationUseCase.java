package com.backend.port.in;

/**
 * Defines the contract for authentication use cases in the application,
 * such as login, signup, OAuth2 login, logout, and password management.
 * Implementations handle user identity and session management.
 */
public interface AuthenticationUseCase {
    /**
     * Authenticates a user using traditional credentials (email and password).
     *
     * @param email    The user's email address.
     * @param password The user's password.
     * @return A JWT token or session token representing the authenticated session.
     */
    String login(String email, String password);

    /**
     * Registers a new user with username, email, and password.
     *
     * @param username The user's chosen username.
     * @param email    The user's email address.
     * @param password The user's chosen password.
     * @return A JWT token or session token for the newly registered user.
     */
    String signUp(String username, String email, String password);

    /**
     * Authenticates or registers a user via an OAuth2 provider (e.g., Google, Facebook).
     * Usually called after obtaining an access token from the provider.
     *
     * @param provider    The OAuth2 provider's name (e.g., "google", "facebook").
     * @param accessToken The OAuth2 access token obtained from the provider.
     * @return A JWT token or session token representing the authenticated session.
     */
    String loginOAuth2(String provider, String accessToken);

    /**
     * Logs out the user by invalidating the session or token.
     *
     * @param token The token or session identifier to invalidate.
     */
    void logout(String token);

    /**
     * Initiates a password reset process for the specified email address.
     * Typically, sends a password reset link or code to the user's email.
     *
     * @param email The user's email address.
     */
    void requestPasswordReset(String email);

    /**
     * Resets the user's password using a token (usually sent by email) and a new password.
     *
     * @param token       The password reset token.
     * @param newPassword The user's new password.
     */
    void resetPassword(String token, String newPassword);
}
