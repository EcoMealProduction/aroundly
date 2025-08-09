package com.backend.adapter.in.dto.request;

public record RegistrationRequestDto(
        String username,
        String email,
        String password) { }
