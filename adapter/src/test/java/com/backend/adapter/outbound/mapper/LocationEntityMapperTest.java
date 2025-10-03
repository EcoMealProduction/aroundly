package com.backend.adapter.outbound.mapper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = LocationEntityMapper.class)
@ExtendWith(SpringExtension.class)
public class LocationEntityMapperTest {

  @Autowired private LocationEntityMapper mapper;

  @Test
  void testToLocationEntity() {

  }
}
