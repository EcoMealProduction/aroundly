package com.backend.adapter.in.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Registration response, showing a successful or unsuccessful creation of account")
public record RegistrationResponseDto(
    @Schema(
        description = "The response message of actor's registration",
        example = "Account created successfully!"
    )
    String message) { }
