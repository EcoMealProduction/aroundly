package com.backend.adapter.in.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

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
@Schema(description = "Login response containing JWT tokens and user information")
public record LoginResponseDto(
        @Schema(
                description = "JWT access token for authenticated API requests",
                example = "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJrZXlfaWQifQ..."
        )
        String accessToken,
        
        @Schema(
                description = "Type of the authentication token",
                example = "Bearer"
        )
        String tokenType,
        
        @Schema(
                description = "Number of seconds until the access token expires",
                example = "3600"
        )
        long expiresIn,
        
        @Schema(
                description = "Refresh token for obtaining new access tokens",
                example = "eyJhbGciOiJIUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJyZWZyZXNoX2tleSJ9..."
        )
        String refreshToken,
        
        @Schema(
                description = "Authenticated user's username",
                example = "john_doe"
        )
        String username,
        
        @Schema(
                description = "Authenticated user's email address",
                example = "john.doe@example.com"
        )
        String email
) {
}
