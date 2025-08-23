package com.backend.in.mapper.response;

import static com.backend.in.mapper.MapperFixtures.DOMAIN_OLD_INCIDENT;
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
public class OldIncidentDetailedResponseMapperTest {

  @Autowired private IncidentDetailedResponseMapper mapper;

  @Test
  void testToDto() {
    IncidentDetailedResponseDto dto = mapper.toDto(DOMAIN_OLD_INCIDENT);

    assertEquals(dto.title(), DOMAIN_OLD_INCIDENT.title());
    assertEquals(dto.description(), DOMAIN_OLD_INCIDENT.description());
    assertEquals(dto.metadata().username(), DOMAIN_OLD_INCIDENT.metadata().actor().username());
    assertEquals(dto.metadata().location().address(), DOMAIN_OLD_INCIDENT.metadata().oldLocation().address());
    assertEquals(dto.metadata().createdAt().getMinute(), DOMAIN_OLD_INCIDENT.metadata().createdAt().getMinute());
    assertEquals(dto.comments().getFirst().text(), DOMAIN_OLD_INCIDENT.comments().getFirst().text());
    assertEquals(0, dto.reaction().likes());
    assertEquals(0, dto.reaction().dislikes());
  }
}
