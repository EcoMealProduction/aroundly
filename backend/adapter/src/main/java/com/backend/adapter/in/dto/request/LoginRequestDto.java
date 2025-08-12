package com.backend.adapter.in.dto.request;

/**
 * Data Transfer Object representing a user login request.
 * 
 * This record encapsulates the credentials required for user authentication,
 * supporting both username and email-based login.
 * 
 * @param usernameOrEmail The user's username or email address used for login
 * @param password The user's password for authentication
 * 
 * @since 1.0
 * @author Backend Team
 */
public record LoginRequestDto(
        String usernameOrEmail,
        String password) {
}
