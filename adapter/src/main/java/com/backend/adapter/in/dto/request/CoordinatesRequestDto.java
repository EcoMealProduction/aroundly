package com.backend.adapter.in.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Request DTO for providing coordinate data.
 *
 * @param lat latitude of the coordinate
 * @param lon longitude of the coordinate
 */
@Schema(description = "Request containing geographic coordinates for location-based operations")
public record CoordinatesRequestDto(
    @Schema(
        description = "Latitude coordinate in decimal degrees",
        example = "45.4642"
    )
    double lat,

    @Schema(
        description = "Longitude coordinate in decimal degrees",
        example = "9.1900"
    )
    double lon) { }
