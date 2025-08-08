package com.backend.services;

import com.backend.domain.happening.Happening;
import com.backend.domain.happening.Incident;
import com.backend.port.out.HappeningRepository;
import com.backend.services.happening.HappeningService;
import com.backend.services.happening.HappeningStrategyResolver;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.backend.Fixtures.validIncident;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class HappeningServiceTest {

    private static final int INCIDENT_ID = 1;

    @Mock private HappeningRepository happeningRepository;
    @Mock private HappeningStrategyResolver happeningStrategyResolver;
    @InjectMocks private HappeningService happeningService;

    @Test
    void testFindAllInGivenRange() {
        final int oneKm = 1;
        when(happeningRepository.findByAllInGivenRange(oneKm)).thenReturn(Collections.singletonList(validIncident));
        List<Happening> foundIncident = happeningService.findAllInGivenRange(oneKm);

        verify(happeningRepository, times(1)).findByAllInGivenRange(oneKm);
        assertEquals(List.of(validIncident), foundIncident);
    }

    @Test
    void testFindById() {
        when(happeningRepository.findById(INCIDENT_ID)).thenReturn(Optional.ofNullable(validIncident));
        Happening foundIncident = happeningService.findById(INCIDENT_ID);

        verify(happeningRepository, times(1)).findById(INCIDENT_ID);
        assertEquals(validIncident, foundIncident);
    }

    @Test
    void testCreateHappening() {
        when(happeningRepository.save(validIncident)).thenReturn(validIncident);
        Happening createdHappening = happeningService.create(validIncident);

        verify(happeningRepository, times(1)).save(validIncident);
        assertEquals(validIncident, createdHappening);
    }

    @Test
    void testExtendIncidentLifespan() {
        when(happeningRepository.findById(INCIDENT_ID)).thenReturn(Optional.ofNullable(validIncident));

        assertNotNull(validIncident);
        Incident expectedExtendedLifespanIncident = validIncident.confirmIncident();
        when(happeningRepository.save(expectedExtendedLifespanIncident)).thenReturn(expectedExtendedLifespanIncident);
        Incident extendedLifespanIncident = happeningService.extendIncidentLifespan(INCIDENT_ID);

        verify(happeningRepository, times(1)).findById(INCIDENT_ID);
        verify(happeningRepository, times(1)).save(expectedExtendedLifespanIncident);

        assertEquals(expectedExtendedLifespanIncident, extendedLifespanIncident);
    }

    @Test
    void testUpdateHappening() {
        when(happeningRepository.findById(INCIDENT_ID)).thenReturn(Optional.ofNullable(validIncident));
        assertNotNull(validIncident);
        Incident expectedUpdatedIncident = validIncident.toBuilder()
                .title("new title for incident")
                .build();
        when(happeningStrategyResolver.update(validIncident, expectedUpdatedIncident)).thenReturn(expectedUpdatedIncident);
        when(happeningRepository.save(expectedUpdatedIncident)).thenReturn(expectedUpdatedIncident);
        Happening updatedHappening = happeningService.update(INCIDENT_ID, expectedUpdatedIncident);

        assertEquals(expectedUpdatedIncident, updatedHappening);
    }

    @Test
    void testDeleteIncident() {
        happeningService.delete(INCIDENT_ID);
        verify(happeningRepository, times(1)).deleteById(INCIDENT_ID);
    }
}
