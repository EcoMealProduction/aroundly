package com.backend.in.mapper.response;

import static com.backend.in.mapper.MapperFixtures.commentDomain;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.backend.adapter.in.dto.response.CommentResponseDto;
import com.backend.adapter.in.mapper.response.CommentResponseMapper;
import com.backend.adapter.in.mapper.response.CommentResponseMapperImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
  CommentResponseMapperImpl.class
})
public class CommentResponseMapperTest {

  @Autowired private CommentResponseMapper mapper;

  @Test
  void testToDto() {
    CommentResponseDto dto = mapper.toDto(commentDomain);

    assertEquals(dto.text(), commentDomain.text());
  }
}
