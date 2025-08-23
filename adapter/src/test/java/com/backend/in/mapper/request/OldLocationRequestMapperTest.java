package com.backend.in.mapper.request;

import static com.backend.in.mapper.MapperFixtures.locationRequestDto;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.backend.adapter.in.mapper.request.LocationRequestMapper;
import com.backend.adapter.in.mapper.request.LocationRequestMapperImpl;
import com.backend.domain.old.OldLocation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = LocationRequestMapperImpl.class)
@ExtendWith(SpringExtension.class)
public class OldLocationRequestMapperTest {

  @Autowired private LocationRequestMapper mapper;

  @Test
  public void testToDomain() {
    OldLocation domain = mapper.toDomain(locationRequestDto);

    assertEquals(domain.address(), locationRequestDto.address());
    assertEquals(domain.latitude(), locationRequestDto.latitude());
    assertEquals(domain.longitude(), locationRequestDto.longitude());
  }
}
