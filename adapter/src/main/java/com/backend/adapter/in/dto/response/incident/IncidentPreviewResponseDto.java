package com.backend.adapter.in.dto.response.incident;

import com.backend.domain.media.Media;
import java.util.Set;
import lombok.Builder;

/**
 * Response DTO for previewing an incident with minimal details.
 *
 * @param title short title of the incident
 * @param media related media (images, videos, etc.)
 */
@Builder(toBuilder = true)
public record IncidentPreviewResponseDto(
    String title,
    Set<Media> media) {
}
