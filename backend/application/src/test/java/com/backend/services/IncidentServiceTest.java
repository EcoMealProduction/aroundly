package com.backend.services;

import com.backend.domain.happening.Incident;
import com.backend.port.out.IncidentRepository;
import com.backend.services.happening.IncidentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static com.backend.Fixtures.validIncident;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class IncidentServiceTest {

    private static final int INCIDENT_ID = 1;

    @Mock private IncidentRepository incidentRepository;
    @InjectMocks private IncidentService incidentService;

    @Test
    void testFindAllInGivenRange() {
        double lat = 47.0101;
        double lon = 28.8576;
        double radius = 1500.0;
        final int oneKm = 1;
        when(incidentRepository.findByAllInGivenRange(lat, lon, radius)).thenReturn(List.of(validIncident));
        List<Incident> result = incidentService.findAllInGivenRange(lat, lon, radius);

        assertEquals(result, List.of(validIncident));
    }

    @Test
    void testFindById() {
        when(incidentRepository.findById(INCIDENT_ID)).thenReturn(Optional.ofNullable(validIncident));
        Incident foundIncident = incidentService.findById(INCIDENT_ID);

        verify(incidentRepository, times(1)).findById(INCIDENT_ID);
        assertEquals(validIncident, foundIncident);
    }

    @Test
    void testCreateIncident() {
        when(incidentRepository.save(validIncident)).thenReturn(validIncident);
        Incident createdIncident = incidentService.create(validIncident);

        verify(incidentRepository, times(1)).save(validIncident);
        assertEquals(validIncident, createdIncident);
    }

    @Test
    void testExtendIncidentLifespan() {
        when(incidentRepository.findById(INCIDENT_ID)).thenReturn(Optional.ofNullable(validIncident));

        assertNotNull(validIncident);
        Incident expectedExtendedLifespanIncident = validIncident.confirmIncident();
        when(incidentRepository.save(expectedExtendedLifespanIncident)).thenReturn(expectedExtendedLifespanIncident);
        Incident extendedLifespanIncident = incidentService.extendIncidentLifespan(INCIDENT_ID);

        verify(incidentRepository, times(1)).findById(INCIDENT_ID);
        verify(incidentRepository, times(1)).save(expectedExtendedLifespanIncident);

        assertEquals(expectedExtendedLifespanIncident, extendedLifespanIncident);
    }

    @Test
    void testUpdateIncident() {
        when(incidentRepository.findById(INCIDENT_ID)).thenReturn(Optional.ofNullable(validIncident));
        assertNotNull(validIncident);
        Incident expectedUpdatedIncident = validIncident.toBuilder()
                .title("new title for incident")
                .build();
        when(incidentRepository.save(expectedUpdatedIncident)).thenReturn(expectedUpdatedIncident);
        Incident updatedIncident = incidentService.update(INCIDENT_ID, expectedUpdatedIncident);

        assertEquals(expectedUpdatedIncident, updatedIncident);
    }

    @Test
    void testDeleteIncident() {
        incidentService.delete(INCIDENT_ID);
        verify(incidentRepository, times(1)).deleteById(INCIDENT_ID);
    }
}
