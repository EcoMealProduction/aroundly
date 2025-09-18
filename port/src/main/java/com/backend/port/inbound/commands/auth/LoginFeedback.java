package com.backend.port.inbound.commands.auth;

/**
 * Represents the feedback returned after a successful login attempt.
 *
 * @param accessToken  the JWT access token for authentication
 * @param tokenType    the type of token (e.g., "Bearer")
 * @param expiresIn    the number of seconds until the access token expires
 * @param refreshToken the refresh token used to obtain a new access token
 * @param username     the username of the authenticated user
 * @param email        the email address of the authenticated user
 */
public record LoginFeedback(
    String accessToken,
    String tokenType,
    long expiresIn,
    String refreshToken,
    String username,
    String email) { }
