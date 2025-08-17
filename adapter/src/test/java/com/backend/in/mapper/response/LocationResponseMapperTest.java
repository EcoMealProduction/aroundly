package com.backend.in.mapper.response;

import static com.backend.in.mapper.MapperFixtures.location;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.backend.adapter.in.dto.response.LocationResponseDto;
import com.backend.adapter.in.mapper.response.LocationResponseMapper;
import com.backend.adapter.in.mapper.response.LocationResponseMapperImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = LocationResponseMapperImpl.class)
@ExtendWith(SpringExtension.class)
public class LocationResponseMapperTest {

  @Autowired private LocationResponseMapper mapper;

  @Test
  public void testToDto() {
    LocationResponseDto dto = mapper.toDto(location);
    assertEquals(dto.address(), location.address());
  }
}
