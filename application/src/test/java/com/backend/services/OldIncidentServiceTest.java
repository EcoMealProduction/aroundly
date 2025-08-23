package com.backend.services;

import com.backend.domain.happening.old.OldIncident;
import com.backend.port.out.IncidentRepository;
import com.backend.services.happening.IncidentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static com.backend.Fixtures.VALID_OLD_INCIDENT;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OldIncidentServiceTest {

    private static final int INCIDENT_ID = 1;

    @Mock private IncidentRepository incidentRepository;
    @InjectMocks private IncidentService incidentService;

    @Test
    void testFindAllInGivenRange() {
        double lat = 47.0101;
        double lon = 28.8576;
        double radius = 1500.0;
        final int oneKm = 1;
        when(incidentRepository.findByAllInGivenRange(lat, lon, radius)).thenReturn(List.of(
            VALID_OLD_INCIDENT));
        List<OldIncident> result = incidentService.findAllInGivenRange(lat, lon, radius);

        assertEquals(result, List.of(VALID_OLD_INCIDENT));
    }

    @Test
    void testFindById() {
        when(incidentRepository.findById(INCIDENT_ID)).thenReturn(Optional.ofNullable(
            VALID_OLD_INCIDENT));
        OldIncident foundOldIncident = incidentService.findById(INCIDENT_ID);

        verify(incidentRepository, times(1)).findById(INCIDENT_ID);
        assertEquals(VALID_OLD_INCIDENT, foundOldIncident);
    }

    @Test
    void testCreateIncident() {
        when(incidentRepository.save(VALID_OLD_INCIDENT)).thenReturn(VALID_OLD_INCIDENT);
        OldIncident createdOldIncident = incidentService.create(VALID_OLD_INCIDENT);

        verify(incidentRepository, times(1)).save(VALID_OLD_INCIDENT);
        assertEquals(VALID_OLD_INCIDENT, createdOldIncident);
    }

    @Test
    void testExtendIncidentLifespan() {
        when(incidentRepository.findById(INCIDENT_ID)).thenReturn(Optional.ofNullable(
            VALID_OLD_INCIDENT));

        assertNotNull(VALID_OLD_INCIDENT);
        OldIncident expectedExtendedLifespanOldIncident = VALID_OLD_INCIDENT.confirmIncident();
        when(incidentRepository.save(expectedExtendedLifespanOldIncident)).thenReturn(
            expectedExtendedLifespanOldIncident);
        OldIncident extendedLifespanOldIncident = incidentService.extendIncidentLifespan(INCIDENT_ID);

        verify(incidentRepository, times(1)).findById(INCIDENT_ID);
        verify(incidentRepository, times(1)).save(expectedExtendedLifespanOldIncident);

        assertEquals(expectedExtendedLifespanOldIncident, extendedLifespanOldIncident);
    }

    @Test
    void testUpdateIncident() {
        when(incidentRepository.findById(INCIDENT_ID)).thenReturn(Optional.ofNullable(
            VALID_OLD_INCIDENT));
        assertNotNull(VALID_OLD_INCIDENT);
        OldIncident expectedUpdatedOldIncident = VALID_OLD_INCIDENT.toBuilder()
                .title("new title for incident")
                .build();
        when(incidentRepository.save(expectedUpdatedOldIncident)).thenReturn(
            expectedUpdatedOldIncident);
        OldIncident updatedOldIncident = incidentService.update(INCIDENT_ID,
            expectedUpdatedOldIncident);

        assertEquals(expectedUpdatedOldIncident, updatedOldIncident);
    }

    @Test
    void testDeleteIncident() {
        incidentService.delete(INCIDENT_ID);
        verify(incidentRepository, times(1)).deleteById(INCIDENT_ID);
    }
}
