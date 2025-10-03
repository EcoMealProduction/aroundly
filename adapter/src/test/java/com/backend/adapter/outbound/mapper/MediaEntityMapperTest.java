package com.backend.adapter.outbound.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.backend.adapter.outbound.entity.MediaEntity;
import com.backend.domain.media.Media;
import java.time.OffsetDateTime;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = MediaEntityMapperImpl.class)
@ExtendWith(SpringExtension.class)
class MediaEntityMapperTest {

  @Autowired private MediaEntityMapper mapper;

  @Test
  void testToDomain() {
    MediaEntity entity = new MediaEntity();
    entity.setKey("file.png");
    entity.setContentType("image/png");
    entity.setSize(12345L);
    entity.setCreatedAt(OffsetDateTime.now());

    Media media = mapper.toDomain(entity);

    assertNotNull(media);
    assertEquals("file.png", media.filename());
    assertEquals("image/png", media.contentType());
    assertEquals(12345L, media.size());
  }

  @Test
  void testToEntity() {
    Media media = new Media(54321L, "file.png", "image/jpeg");

    MediaEntity entity = mapper.toEntity(media);
    assertNotNull(entity);
    assertEquals("file.png", entity.getKey());
    assertEquals("image/jpeg", entity.getContentType());
    assertEquals(54321L, entity.getSize());
    assertNull(entity.getCreatedAt(), "createdAt should be ignored and set to null");
  }
}
