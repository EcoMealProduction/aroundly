package com.backend.adapter.inbound.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Set;
import lombok.Builder;
import lombok.NonNull;
import org.springframework.web.multipart.MultipartFile;

/**
 * Request DTO for creating a new incident.
 *
 * @param title       short title of the incident
 * @param description detailed description of the incident
 * @param files       media files attached to the incident
 * @param lat         latitude of the incident location
 * @param lon         longitude of the incident location
 */
@Schema(description = "Request to create a new incident with location and media attachments")
@Builder(toBuilder = true)
public record IncidentRequestDto(
    @Schema(
        description = "Short descriptive title of the incident",
        example = "Road closure due to construction"
    )
    @NonNull String title,

    @Schema(
        description = "Detailed description of the incident",
        example = "Main street is closed from 9 AM to 5 PM due to water pipe maintenance work"
    )
    @NonNull String description,

    @Schema(
        description = "Collection of related media files (images, videos, etc.) to attach to the incident"
    )
    @NonNull Set<MultipartFile> files,

    @Schema(
        description = "Latitude coordinate of the incident location in decimal degrees",
        example = "45.4642"
    )
    double lat,

    @Schema(
        description = "Longitude coordinate of the incident location in decimal degrees",
        example = "9.1900"
    )
    double lon) { }
