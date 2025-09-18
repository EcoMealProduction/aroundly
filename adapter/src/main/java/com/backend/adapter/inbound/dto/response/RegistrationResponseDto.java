package com.backend.adapter.inbound.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Response DTO carrying the result of a successful user registration.
 *
 * @param userId unique identifier of the newly created user account
 */
@Schema(description = "Response containing the result of user registration")
public record RegistrationResponseDto(
    @Schema(
        description = "Unique identifier of the newly created user account",
        example = "usr_12345abc-def6-7890-ghij-klmnopqrstuv"
    )
    String userId) {

    public String successfulRegistrationMessage() {
        return "User was successful registered with ID: " + userId;
    }
}
