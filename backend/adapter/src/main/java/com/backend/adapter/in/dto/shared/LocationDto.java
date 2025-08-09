package com.backend.adapter.in.dto.shared;

import lombok.Builder;
import lombok.NonNull;

import java.math.BigDecimal;

/**
 * Data Transfer Object representing a geographical location.
 * <p>
 * Contains coordinate information and optional human-readable address
 * for identifying specific locations on Earth.
 * </p>
 *
 * @param latitude   North-south position in decimal degrees
 * @param longitude  East-west position in decimal degrees
 * @param address    Human-readable location description (optional)
 */
@Builder(toBuilder = true)
public record LocationDto(
        @NonNull BigDecimal latitude,
        @NonNull BigDecimal longitude,
        String address) {}
