package com.backend.in.mapper.request;

import static com.backend.in.mapper.MapperFixtures.mediaDomain;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.backend.adapter.in.dto.media.MediaRefDto;
import com.backend.adapter.in.mapper.MediaRefMapper;
import com.backend.adapter.in.mapper.MediaRefMapperImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {MediaRefMapperImpl.class})
@ExtendWith(SpringExtension.class)
public class MediaMapperTest {

  @Autowired private MediaRefMapper mapper;

  @Test
  public void testMapToDto() {
    MediaRefDto mediaRefDto = mapper.toDto(mediaDomain);

    assertEquals(mediaRefDto.uri(), mediaDomain.uri());
  }

}
