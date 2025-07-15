package com.backend.domain;

import com.backend.shared.Location;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static com.backend.domain.Fixtures.*;
import static org.junit.jupiter.api.Assertions.*;

public class LocationTest {

    @Test
    void testValidLocationCreatedSuccessfully() {
        Location location = validLocation;

        assertEquals(BigDecimal.valueOf(47.0), location.latitude());
        assertEquals(BigDecimal.valueOf(28.0), location.longitude());
        assertEquals("Strada Stefan cel Mare 1, Chisinau", location.address());
    }

    @Test
    void testLatitudeOutOfRangeThrowsException() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () ->
                validLocation.toBuilder().latitude(BigDecimal.valueOf(44.0)).build()
        );
        assertEquals("Latitude must be within Moldova.", ex.getMessage());
    }

    @Test
    void testLongitudeOutOfRangeThrowsException() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () ->
                Fixtures.validLocation.toBuilder().longitude(BigDecimal.valueOf(31.0)).build()
        );
        assertEquals("Longitude must be within Moldova.", ex.getMessage());
    }

    @Test
    void testAddressTooShortThrowsException() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () ->
                Fixtures.validLocation.toBuilder().address("Str. x").build()
        );
        assertEquals("Address must have at least 10 characters.", ex.getMessage());
    }
}
