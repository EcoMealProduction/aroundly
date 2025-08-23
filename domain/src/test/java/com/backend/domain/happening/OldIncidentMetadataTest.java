package com.backend.domain.happening;

import com.backend.domain.happening.metadata.IncidentMetadata;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static com.backend.domain.Fixtures.incidentMetadata;
import static org.junit.jupiter.api.Assertions.*;

public class OldIncidentMetadataTest {

    @Test
    void testIsExpired() {
        IncidentMetadata validIncidentNotExpired = incidentMetadata.toBuilder()
                .expirationTime(LocalDateTime.now().plusMinutes(10))
                .build();

        assertFalse(validIncidentNotExpired.isExpired());
    }

    @Test
    void testExpirationTimeBeforeCreatedAtThrowsException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                incidentMetadata.toBuilder()
                        .createdAt(LocalDateTime.now().plusMinutes(10))
                        .expirationTime(LocalDateTime.now().plusMinutes(5))
                        .build()
        );
        assertEquals("Expiration time cannot be before createdAt.", exception.getMessage());
    }

    @Test
    void testExpirationTimeBeforeNowThrowsException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                incidentMetadata.toBuilder()
                        .createdAt(LocalDateTime.now().minusMinutes(30))
                        .expirationTime(LocalDateTime.now().minusMinutes(1))
                        .build()
        );
        assertEquals("Expiration time cannot be before now.", exception.getMessage());
    }
}
