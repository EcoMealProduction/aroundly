package com.backend.adapter.in.dto.response.incident;

import lombok.Builder;

@Builder(toBuilder = true)
public record IncidentPreviewResponseDto(
    String title,
    IncidentMetadataResponseDto metadata) {
}
