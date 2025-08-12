package com.backend.adapter.in.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Data Transfer Object representing a user login request.
 * 
 * This record encapsulates the credentials required for user authentication,
 * supporting both username and email-based login.
 * 
 * @param usernameOrEmail The user's username or email address used for login
 * @param password The user's password for authentication
 */
@Schema(description = "User login credentials")
public record LoginRequestDto(
        @Schema(
                description = "Username or email address for authentication", 
                example = "john_doe or john.doe@example.com",
                required = true
        )
        String usernameOrEmail,
        
        @Schema(
                description = "User password for authentication", 
                example = "SecurePass123!",
                required = true
        )
        String password) {
}
