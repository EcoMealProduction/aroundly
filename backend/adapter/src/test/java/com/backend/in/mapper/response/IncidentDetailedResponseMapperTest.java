package com.backend.in.mapper.response;

import static com.backend.in.mapper.MapperFixtures.domainIncident;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.backend.adapter.in.dto.response.incident.IncidentDetailedResponseDto;
import com.backend.adapter.in.mapper.MediaRefMapperImpl;
import com.backend.adapter.in.mapper.response.CommentResponseMapperImpl;
import com.backend.adapter.in.mapper.response.IncidentDetailedResponseMapper;
import com.backend.adapter.in.mapper.response.IncidentDetailedResponseMapperImpl;
import com.backend.adapter.in.mapper.response.IncidentMetadataResponseMapperImpl;
import com.backend.adapter.in.mapper.response.LocationResponseMapperImpl;
import com.backend.adapter.in.mapper.response.SentimentEngagementResponseMapperImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
    IncidentDetailedResponseMapperImpl.class,
    IncidentMetadataResponseMapperImpl.class,
    LocationResponseMapperImpl.class,
    CommentResponseMapperImpl.class,
    SentimentEngagementResponseMapperImpl.class,
    MediaRefMapperImpl.class,
})
public class IncidentDetailedResponseMapperTest {

  @Autowired private IncidentDetailedResponseMapper mapper;

  @Test
  void testToDto() {
    IncidentDetailedResponseDto dto = mapper.toDto(domainIncident);

    assertEquals(dto.title(), domainIncident.title());
    assertEquals(dto.description(), domainIncident.description());
    assertEquals(dto.metadata().username(), domainIncident.metadata().actor().username());
    assertEquals(dto.metadata().location().address(), domainIncident.metadata().location().address());
    assertEquals(dto.metadata().createdAt().getMinute(), domainIncident.metadata().createdAt().getMinute());
    assertEquals(dto.comments().getFirst().text(), domainIncident.comments().getFirst().text());
    assertEquals(0, dto.reaction().likes());
    assertEquals(0, dto.reaction().dislikes());
  }
}
