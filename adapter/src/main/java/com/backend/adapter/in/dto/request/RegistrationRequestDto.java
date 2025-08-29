package com.backend.adapter.in.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "User registration request data")
public record RegistrationRequestDto(
        @Schema(
            description = "Username for the new account",
            example = "john_doe"
        )
        String username,
        
        @Schema(
            description = "Email address for the new account",
            example = "john.doe@example.com"
        )
        String email,
        
        @Schema(
                description = "Password for the new account. Must meet the following requirements: " +
                        "minimum 8 characters, at least 1 digit, at least 1 uppercase letter, " +
                        "at least 1 special character (!@#$%^&*()_+-={}[]|:\"';'<>?,./), " +
                        "and cannot be the same as username",
                example = "SecurePass123!",
                minLength = 8,
                pattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*()_+\\\\-={}\\\\[\\\\]|:\\\";'<>?,.\\\\/]).*$"
        )
        String password) { }
