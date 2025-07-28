package com.backend.in.dto.shared;

import lombok.Builder;
import lombok.NonNull;

import java.time.LocalDateTime;

@Builder(toBuilder = true)
public record IncidentMetadataDto(
        @NonNull String authorUsername,
        @NonNull LocationDto location,
        @NonNull LocalDateTime createdAt,
        @NonNull LocalDateTime expirationTime
) {}
