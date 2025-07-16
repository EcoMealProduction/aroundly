package com.backend.shared;

import lombok.Builder;
import lombok.NonNull;

import java.math.BigDecimal;

/**
 * Represents a geographical location with latitude, longitude, and an optional textual address.
 * Used to describe the physical position of events, incidents, or users.
 */
@Builder(toBuilder = true)
public record Location(
        @NonNull BigDecimal latitude,
        @NonNull BigDecimal longitude,
        String address
) {

    /**
     * Constructs a {@code Location} instance with validation.
     *
     * @throws IllegalArgumentException if:
     *   @param latitude is not within the geographic bounds of Moldova (45.467 to 48.491)
     *   @param longitude is not within the geographic bounds of Moldova (26.616 to 30.133)
     *   @param address is non-null and shorter than 10 characters
     */
    public Location {
        if (latitude.compareTo(BigDecimal.valueOf(45.467)) < 0 ||
                latitude.compareTo(BigDecimal.valueOf(48.491)) > 0)
            throw new IllegalArgumentException("Latitude must be within Moldova.");

        if (longitude.compareTo(BigDecimal.valueOf(26.616)) < 0 ||
                longitude.compareTo(BigDecimal.valueOf(30.133)) > 0)
            throw new IllegalArgumentException("Longitude must be within Moldova.");

        if (address != null && address.length() < 10)
            throw new IllegalArgumentException("Address must have at least 10 characters.");
    }
}
