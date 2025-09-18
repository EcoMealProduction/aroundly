package com.backend.adapter.inbound.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Response DTO carrying coordinate details.
 *
 * @param lat     latitude of the coordinate
 * @param lon     longitude of the coordinate
 * @param address human-readable formatted address
 */
@Schema(description = "Response containing geographic coordinate information with human-readable address")
public record CoordinateResponseDto(
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
        description = "Human-readable formatted address for the coordinate",
        example = "Via del Corso, 20121 Milan, Italy"
    )
    String address) { }
