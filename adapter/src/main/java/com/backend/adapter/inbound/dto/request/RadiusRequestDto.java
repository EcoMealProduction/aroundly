package com.backend.adapter.inbound.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Request DTO for querying locations within a given radius.
 *
 * @param lat    latitude of the center point
 * @param lon    longitude of the center point
 * @param radius search radius in kilometers
 */
@Schema(description = "Request containing geographic coordinates and search radius for location-based queries")
public record RadiusRequestDto(
    @Schema(
        description = "Latitude coordinate in decimal degrees",
        example = "45.4642"
    )
    double lat,

    @Schema(
        description = "Longitude coordinate in decimal degrees",
        example = "9.1900"
    )
    double lon,

    @Schema(
        description = "Search radius in meters from the specified coordinates",
        example = "1000.0"
    )
    double radius) { }
