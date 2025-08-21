package com.backend.in.mapper.response;

import static com.backend.in.mapper.MapperFixtures.plainSentimentEngagementDomain;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.backend.adapter.in.dto.response.SentimentEngagementResponseDto;
import com.backend.adapter.in.mapper.response.SentimentEngagementResponseMapper;
import com.backend.adapter.in.mapper.response.SentimentEngagementResponseMapperImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
    SentimentEngagementResponseMapperImpl.class
})
public class SentimentEngagementResponseMapperTest {

  @Autowired private SentimentEngagementResponseMapper mapper;

  @Test
  void testToDto() {
    SentimentEngagementResponseDto dto = mapper.toDto(plainSentimentEngagementDomain);

    assertEquals(dto.likes(), plainSentimentEngagementDomain.likes());
    assertEquals(dto.dislikes(), plainSentimentEngagementDomain.dislikes());
  }
}
