package com.backend.in.dto.request;

import com.backend.in.dto.shared.IncidentMetadataDto;
import lombok.Builder;
import lombok.NonNull;

@Builder(toBuilder = true)
public record IncidentRequestDto(
        @NonNull String title,
        @NonNull String description,
        @NonNull IncidentMetadataDto incidentMetadata
        ) {}
