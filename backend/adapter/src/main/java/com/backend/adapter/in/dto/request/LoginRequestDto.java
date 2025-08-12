package com.backend.adapter.in.dto.request;

public record LoginRequestDto(
        String usernameOrEmail,
        String password) {
}
