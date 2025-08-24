package com.backend.services;

import com.backend.domain.actor.ActorId;
import com.backend.domain.happening.Happening;
import com.backend.domain.happening.Incident;
import com.backend.domain.location.Location;
import com.backend.domain.location.LocationId;
import com.backend.domain.media.Media;
import com.backend.domain.media.MediaKind;
import com.backend.port.inbound.commands.CreateIncidentCommand;
import com.backend.port.inbound.commands.RadiusCommand;
import com.backend.port.outbound.IncidentRepository;
import com.backend.port.outbound.LocationRepository;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class IncidentServiceTest {

    @Mock private IncidentRepository incidentRepository;
    @Mock private LocationRepository locationRepository;
    @InjectMocks private IncidentService incidentService;

    @Test
    void testFindAllInGivenRange() throws URISyntaxException {
        double lat = 47.0101;
        double lon = 28.8576;
        double radius = 1500.0;

        RadiusCommand radiusCommand = new RadiusCommand(lat, lon, radius);

        when(incidentRepository.findAllInGivenRange(lat, lon, radius))
            .thenReturn(List.of(createIncident()));
        List<Incident> result = incidentService.findAllInGivenRange(radiusCommand);

        assertEquals(result, List.of(createIncident()));
    }

    @Test
    void testFindByActorId() throws URISyntaxException {
        final ActorId actorId = new ActorId("abc-123");
        when(incidentRepository.findByUserId(actorId.id())).thenReturn(List.of(createIncident()));

        List<Happening> result = incidentService.findByActorId(actorId.id());

        assertEquals(result, List.of(createIncident()));
    }

    @Test
    void testFindById() throws URISyntaxException {
        when(incidentRepository.findById(1L)).thenReturn(Optional.ofNullable(
            createIncident()));
        Happening result = incidentService.findById(1L);

        assertEquals(createIncident(), result);
    }

    @Test
    void testCreateIncident() throws URISyntaxException {
        final double latitude = createIncidentCommand().lat();
        final double longitude = createIncidentCommand().lon();

        when(locationRepository.findByCoordinate(latitude, longitude))
            .thenReturn(createLocation());
        when(incidentRepository.save(createIncident())).thenReturn(createIncident());
        Incident result = incidentService.create(createIncidentCommand());

        assertEquals(createIncident(), result);
    }

    @Test
    void testExtendIncidentLifespan() {

    }

    @Test
    void testUpdateIncident() throws URISyntaxException {
        when(incidentRepository.findById(1L))
            .thenReturn(Optional.ofNullable(createIncident()));
        Incident expectedUpdatedOldIncident = createIncident().toBuilder()
                .title("new title for incident")
                .build();

        when(incidentRepository.save(expectedUpdatedOldIncident))
            .thenReturn(expectedUpdatedOldIncident);

        Incident updatedOldIncident = incidentService.update(1L, createUpdatedIncidentCommand());

        assertEquals(expectedUpdatedOldIncident, updatedOldIncident);
    }

    @Test
    void testDeleteIncident() throws URISyntaxException {
        incidentService.deleteById(1L);
        verify(incidentRepository, times(1)).deleteById(1L);
    }

    private Incident createIncident() throws URISyntaxException {
        return Incident.builder()
            .actorId(new ActorId("id"))
            .locationId(new LocationId(1L))
            .title("title")
            .description("description")
            .media(Set.of(new Media(MediaKind.IMAGE, "type", new URI("/path/"))))
            .build();
    }

    private CreateIncidentCommand createIncidentCommand() throws URISyntaxException {
        return new CreateIncidentCommand(
            "title",
            "new title for incident",
            Set.of(new Media(MediaKind.IMAGE, "type", new URI("path"))),
            47.0101,
            28.8576);
    }

    private CreateIncidentCommand createUpdatedIncidentCommand() throws URISyntaxException {
        return new CreateIncidentCommand(
            "title",
            "new title for incident",
            Set.of(new Media(MediaKind.IMAGE, "type", new URI("path"))),
            47.0101,
            28.8576);
    }

    private Location createLocation() {
        return new Location(
            new LocationId(1L),
            47.0101, 28.8576,
            "str. new 1");
    }
}
