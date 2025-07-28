package com.backend.in.dto.request;

import com.backend.in.dto.shared.EventMetadataDto;
import lombok.Builder;
import lombok.NonNull;

@Builder(toBuilder = true)
public record EventRequestDto(
        @NonNull String title,
        @NonNull String description,
        @NonNull EventMetadataDto eventMetadata
        ) {}
