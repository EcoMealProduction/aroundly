package com.backend.adapter.in.dto.response.incident;

import com.backend.adapter.in.dto.media.MediaRefDto;
import com.backend.adapter.in.dto.response.LocationResponseDto;
import java.time.LocalDateTime;
import java.util.Set;
import lombok.Builder;

@Builder(toBuilder = true)
public record IncidentMetadataResponseDto(
    String username,
    Set<MediaRefDto> media,
    LocationResponseDto location,
    LocalDateTime createdAt) {
}
