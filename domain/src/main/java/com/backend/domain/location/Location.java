package com.backend.domain.location;

import java.math.BigDecimal;
import lombok.NonNull;

/**
 * Represents a geographic location with an identifier,
 * longitude, and latitude.
 *
 * @param id        the unique identifier of the location
 * @param longitude the longitude coordinate
 * @param latitude  the latitude coordinate
 */
public record Location(@NonNull LocationId id, double longitude, double latitude) { }
