package com.backend.in.dto.shared;

import lombok.Builder;
import lombok.NonNull;

import java.time.LocalDateTime;

@Builder(toBuilder = true)
public record EventMetadataDto(
        @NonNull String authorUsername,
        @NonNull LocationDto location,
        @NonNull LocalDateTime startTime,
        @NonNull LocalDateTime endTime
) {}
