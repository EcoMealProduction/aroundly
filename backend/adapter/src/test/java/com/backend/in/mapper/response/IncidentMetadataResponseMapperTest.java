package com.backend.in.mapper.response;

import static com.backend.in.mapper.MapperFixtures.incidentMetadata;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.backend.adapter.in.dto.response.incident.IncidentMetadataResponseDto;
import com.backend.adapter.in.mapper.MediaRefMapperImpl;
import com.backend.adapter.in.mapper.response.IncidentMetadataResponseMapper;
import com.backend.adapter.in.mapper.response.IncidentMetadataResponseMapperImpl;
import com.backend.adapter.in.mapper.response.LocationResponseMapperImpl;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
    IncidentMetadataResponseMapperImpl.class,
    MediaRefMapperImpl.class,
    LocationResponseMapperImpl.class
})
public class IncidentMetadataResponseMapperTest {

  @Autowired private IncidentMetadataResponseMapper mapper;

  @Test
  void testToDto() {
    IncidentMetadataResponseDto dto = mapper.toDto(incidentMetadata);

    assertEquals(dto.username(), incidentMetadata.actor().username());
    assertEquals(dto.media().size(), incidentMetadata.media().size());
    assertEquals(incidentMetadata.location().address(), dto.location().address());
  }
}
