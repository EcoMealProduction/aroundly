package com.backend.adapter.inbound.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.backend.adapter.inbound.dto.request.IncidentRequestDto;
import com.backend.adapter.inbound.dto.response.incident.IncidentPreviewResponseDto;
import com.backend.adapter.inbound.mapper.assembler.UploadMediaMapperAssembler;
import com.backend.domain.actor.ActorId;
import com.backend.domain.happening.Incident;
import com.backend.domain.location.LocationId;
import com.backend.domain.media.Media;
import com.backend.port.inbound.commands.CreateIncidentCommand;
import java.nio.charset.StandardCharsets;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.multipart.MultipartFile;

@ContextConfiguration(classes = {IncidentMapperImpl.class, UploadMediaMapperAssembler.class})
@ExtendWith(SpringExtension.class)
class IncidentMapperTest {

  @Autowired private IncidentMapper mapper;

  @Test
  void testToCreateIncidentCommand() {
    IncidentRequestDto incidentRequestDto = createIncidentRequestDto();
    CreateIncidentCommand createIncidentCommand = mapper.toCreateIncidentCommand(incidentRequestDto);

    assertEquals(createIncidentCommand.title(), incidentRequestDto.title());
    assertEquals(createIncidentCommand.description(), incidentRequestDto.description());
    assertEquals(createIncidentCommand.lat(), incidentRequestDto.lat());
    assertEquals(createIncidentCommand.lon(), incidentRequestDto.lon());
    assertNotNull(createIncidentCommand.media());
    assertEquals(createIncidentCommand.media().size(), createFiles().size());
  }

  @Test
  void testToIncidentPreviewResponseDto() {
    Incident incident = createIncident();

    IncidentPreviewResponseDto incidentPreviewResponseDto =
        mapper.toIncidentPreviewResponseDto(incident);

    assertEquals(incident.getTitle(), incidentPreviewResponseDto.title());
    assertEquals(incident.getMedia(), incidentPreviewResponseDto.media());
  }

  private Incident createIncident() {
    return new Incident(
        new ActorId("id"),
        new LocationId(1L),
        "title",
        "description",
        createMedia()
    );
  }

  private IncidentRequestDto createIncidentRequestDto() {
    return new IncidentRequestDto(
      "title",
      "description",
      createFiles(),
      12.12, 43.43
    );
  }

  private Set<Media> createMedia() {
    return Set.of(new Media(3L, "file", "type"));
  }

  private Set<MultipartFile> createFiles() {
    byte[] data = "hello".getBytes(StandardCharsets.UTF_8);
    MultipartFile mockMultipartFile = new MockMultipartFile(
        "files",
        "ro ad(1).png",
        "image/png",
        data);
    return Set.of(mockMultipartFile);
  }

}
