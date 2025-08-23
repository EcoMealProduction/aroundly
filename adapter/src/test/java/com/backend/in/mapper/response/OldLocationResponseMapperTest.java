package com.backend.in.mapper.response;

import static com.backend.in.mapper.MapperFixtures.OLD_LOCATION;
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
public class OldLocationResponseMapperTest {

  @Autowired private LocationResponseMapper mapper;

  @Test
  public void testToDto() {
    LocationResponseDto dto = mapper.toDto(OLD_LOCATION);
    assertEquals(dto.address(), OLD_LOCATION.address());
  }
}
