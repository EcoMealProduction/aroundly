package com.backend.adapter.in.dto.request;

import com.backend.domain.media.Media;
import java.util.Set;
import lombok.Builder;
import lombok.NonNull;

/**
 * Request DTO for creating a new incident.
 *
 * @param title       short title of the incident
 * @param description detailed description of the incident
 * @param media       media files attached to the incident
 * @param lat         latitude of the incident location
 * @param lon         longitude of the incident location
 */
@Builder(toBuilder = true)
public record IncidentRequestDto(
    @NonNull String title,
    @NonNull String description,
    @NonNull Set<Media> media,
    double lat, double lon) { }
