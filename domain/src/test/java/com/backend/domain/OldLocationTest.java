package com.backend.domain;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static com.backend.domain.Fixtures.*;
import static org.junit.jupiter.api.Assertions.*;

public class OldLocationTest {

    @Test
    void testValidLocationCreatedSuccessfully() {
        assertEquals(BigDecimal.valueOf(47.0), VALID_OLD_LOCATION.latitude());
        assertEquals(BigDecimal.valueOf(28.0), VALID_OLD_LOCATION.longitude());
        assertEquals("Strada Stefan cel Mare 1, Chisinau", VALID_OLD_LOCATION.address());
    }

    @Test
    void testLatitudeOutOfRangeThrowsException() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () ->
                VALID_OLD_LOCATION.toBuilder().latitude(BigDecimal.valueOf(44.0)).build()
        );
        assertEquals("Latitude must be within Moldova.", ex.getMessage());
    }

    @Test
    void testLongitudeOutOfRangeThrowsException() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () ->
                Fixtures.VALID_OLD_LOCATION.toBuilder().longitude(BigDecimal.valueOf(31.0)).build()
        );
        assertEquals("Longitude must be within Moldova.", ex.getMessage());
    }

    @Test
    void testAddressTooShortThrowsException() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () ->
                Fixtures.VALID_OLD_LOCATION.toBuilder().address("Str. x").build()
        );
        assertEquals("Address must have at least 10 characters.", ex.getMessage());
    }
}
