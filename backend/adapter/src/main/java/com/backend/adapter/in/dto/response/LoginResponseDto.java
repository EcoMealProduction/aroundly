package com.backend.adapter.in.dto.response;

public record LoginResponseDto(
        String accessToken,
        String tokenType,
        long expiresIn,
        String refreshToken,
        String username,
        String email
) {
}
