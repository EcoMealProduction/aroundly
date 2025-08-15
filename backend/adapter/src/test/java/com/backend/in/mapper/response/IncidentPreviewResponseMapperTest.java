package com.backend.in.mapper.response;

import static com.backend.in.mapper.MapperFixtures.domainIncident;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.backend.adapter.in.dto.response.incident.IncidentPreviewResponseDto;
import com.backend.adapter.in.mapper.MediaRefMapperImpl;
import com.backend.adapter.in.mapper.response.IncidentMetadataResponseMapperImpl;
import com.backend.adapter.in.mapper.response.IncidentPreviewResponseMapper;
import com.backend.adapter.in.mapper.response.IncidentPreviewResponseMapperImpl;
import com.backend.adapter.in.mapper.response.LocationResponseMapperImpl;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
    IncidentPreviewResponseMapperImpl.class,
    IncidentMetadataResponseMapperImpl.class,
    LocationResponseMapperImpl.class,
    MediaRefMapperImpl.class,
})
public class IncidentPreviewResponseMapperTest {

  @Autowired private IncidentPreviewResponseMapper mapper;

  @Test
  void testToDto() {
    IncidentPreviewResponseDto dto = mapper.toDto(domainIncident);

    assertEquals(dto.title(), domainIncident.title());

    assertNotNull(domainIncident.metadata());
    assertEquals(dto.metadata().username(), domainIncident.metadata().actor().username());
    assertEquals(dto.metadata().media().size(), domainIncident.metadata().media().size());
    assertEquals(dto.metadata().location().address(), domainIncident.metadata().location().address());
  }
}
