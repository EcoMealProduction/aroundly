package com.backend.adapter.in.dto.response;

/**
 * Data Transfer Object representing a successful login response.
 * 
 * This record contains all the authentication tokens and user information
 * returned after a successful authentication with Keycloak.
 * 
 * @param accessToken JWT access token for authenticated requests
 * @param tokenType Type of the token (typically "Bearer")
 * @param expiresIn Number of seconds until the access token expires
 * @param refreshToken Token used to refresh the access token
 * @param username The authenticated user's username
 * @param email The authenticated user's email address
 */
public record LoginResponseDto(
        String accessToken,
        String tokenType,
        long expiresIn,
        String refreshToken,
        String username,
        String email
) {
}
