package com.backend.domain.happening;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static com.backend.domain.Fixtures.eventMetadata;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class EventMetadataTest {

    @Test
    void testStartTimeBeforeNowThrowsException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                eventMetadata.toBuilder()
                        .startTime(LocalDateTime.now().minusMinutes(5))
                        .build());

        assertEquals("Start time cannot be before now.", exception.getMessage());
    }

    @Test
    void testEndTimeBeforeNowThrowsException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                eventMetadata.toBuilder()
                        .endTime(LocalDateTime.now().minusMinutes(5))
                        .build());

        assertEquals("End time cannot be before now.", exception.getMessage());
    }

    @Test
    void testTooShortDurationThrowsException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                eventMetadata.toBuilder()
                        .startTime(LocalDateTime.now().plusMinutes(5))
                        .endTime(LocalDateTime.now().plusMinutes(15))
                        .build());

        assertEquals("Event duration must be at least 30 minutes.", exception.getMessage());
    }
}
