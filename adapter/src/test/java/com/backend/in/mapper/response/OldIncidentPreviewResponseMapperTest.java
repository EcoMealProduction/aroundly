package com.backend.in.mapper.response;

import static com.backend.in.mapper.MapperFixtures.DOMAIN_OLD_INCIDENT;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.backend.adapter.in.dto.response.incident.IncidentPreviewResponseDto;
import com.backend.adapter.in.mapper.MediaRefMapperImpl;
import com.backend.adapter.in.mapper.response.IncidentMetadataResponseMapperImpl;
import com.backend.adapter.in.mapper.response.IncidentPreviewResponseMapper;
import com.backend.adapter.in.mapper.response.IncidentPreviewResponseMapperImpl;
import com.backend.adapter.in.mapper.response.LocationResponseMapperImpl;
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
public class OldIncidentPreviewResponseMapperTest {

  @Autowired private IncidentPreviewResponseMapper mapper;

  @Test
  void testToDto() {
    IncidentPreviewResponseDto dto = mapper.toDto(DOMAIN_OLD_INCIDENT);

    assertEquals(dto.title(), DOMAIN_OLD_INCIDENT.title());

    assertNotNull(DOMAIN_OLD_INCIDENT.metadata());
    assertEquals(dto.metadata().username(), DOMAIN_OLD_INCIDENT.metadata().actor().username());
    assertEquals(dto.metadata().media().size(), DOMAIN_OLD_INCIDENT.metadata().media().size());
    assertEquals(dto.metadata().location().address(), DOMAIN_OLD_INCIDENT.metadata().oldLocation().address());
  }
}
