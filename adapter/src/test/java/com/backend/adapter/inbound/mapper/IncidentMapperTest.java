package com.backend.adapter.inbound.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.backend.adapter.in.dto.request.IncidentRequestDto;
import com.backend.adapter.in.dto.response.incident.IncidentPreviewResponseDto;
import com.backend.adapter.in.mapper.IncidentMapper;
import com.backend.adapter.in.mapper.IncidentMapperImpl;
import com.backend.domain.actor.ActorId;
import com.backend.domain.happening.Incident;
import com.backend.domain.location.LocationId;
import com.backend.domain.media.Media;
import com.backend.domain.media.MediaKind;
import com.backend.port.inbound.commands.CreateIncidentCommand;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = IncidentMapperImpl.class)
@ExtendWith(SpringExtension.class)
class IncidentMapperTest {

  @Autowired private IncidentMapper mapper;

  @Test
  void testToCreateIncidentCommand() throws URISyntaxException {
    IncidentRequestDto incidentRequestDto = createIncidentRequestDto();
    CreateIncidentCommand createIncidentCommand = mapper.toCreateIncidentCommand(incidentRequestDto);

    assertEquals(createIncidentCommand.title(), incidentRequestDto.title());
    assertEquals(createIncidentCommand.description(), incidentRequestDto.description());
    assertEquals(createIncidentCommand.media(), incidentRequestDto.media());
    assertEquals(createIncidentCommand.lat(), incidentRequestDto.lat());
    assertEquals(createIncidentCommand.lon(), incidentRequestDto.lon());
  }

  @Test
  void testToIncidentPreviewResponseDto() throws URISyntaxException {
    Incident incident = createIncident();

    IncidentPreviewResponseDto incidentPreviewResponseDto =
        mapper.toIncidentPreviewResponseDto(incident);

    assertEquals(incident.getTitle(), incidentPreviewResponseDto.title());
    assertEquals(incident.getMedia(), incidentPreviewResponseDto.media());
  }

  private Incident createIncident() throws URISyntaxException {
    return new Incident(
        new ActorId("id"),
        new LocationId(1L),
        "title",
        "description",
        Set.of(new Media(MediaKind.IMAGE, "type", new URI("/path/")))
    );
  }

  private IncidentRequestDto createIncidentRequestDto() throws URISyntaxException {
    return new IncidentRequestDto(
      "title",
      "description",
      Set.of(new Media(MediaKind.IMAGE, "type", new URI("/path/"))),
      12.12, 43.43
    );
  }

}
