package com.backend.adapter.inbound.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.backend.adapter.in.dto.request.CoordinatesRequestDto;
import com.backend.adapter.in.dto.request.RadiusRequestDto;
import com.backend.adapter.in.dto.response.CoordinateResponseDto;
import com.backend.adapter.in.mapper.LocationMapper;
import com.backend.adapter.in.mapper.LocationMapperImpl;
import com.backend.domain.location.Location;
import com.backend.domain.location.LocationId;
import com.backend.port.inbound.commands.CoordinatesCommand;
import com.backend.port.inbound.commands.RadiusCommand;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = LocationMapperImpl.class)
@ExtendWith(SpringExtension.class)
class LocationMapperTest {

  @Autowired private LocationMapper mapper;

  @Test
  void testToRadiusCommand() {
    RadiusRequestDto radiusRequestDto = createRadiusRequestDto();
    RadiusCommand radiusCommand = mapper.toRadiusCommand(radiusRequestDto);

    assertEquals(radiusCommand.lat(), radiusRequestDto.lat());
    assertEquals(radiusCommand.lon(), radiusRequestDto.lon());
    assertEquals(radiusCommand.radius(), radiusRequestDto.radius());
  }

  @Test
  void testToCoordinatesCommand() {
    CoordinatesRequestDto coordinatesRequestDto = createCoordinatesRequestDto();
    CoordinatesCommand coordinatesCommand = mapper.toCoordinatesCommand(coordinatesRequestDto);

    assertEquals(coordinatesRequestDto.lat(), coordinatesCommand.lat());
    assertEquals(coordinatesRequestDto.lon(), coordinatesCommand.lon());
  }

  @Test
  void testToCoordinateResponseDto() {
    Location location = createLocation();
    CoordinateResponseDto coordinateResponseDto = mapper.toCoordinateResponseDto(location);

    assertEquals(location.latitude(), coordinateResponseDto.lat());
    assertEquals(location.longitude(), coordinateResponseDto.lon());
    assertEquals(location.address(), coordinateResponseDto.address());
  }

  private RadiusRequestDto createRadiusRequestDto() {
    return new RadiusRequestDto(11.11, 31.31, 0.5);
  }

  private CoordinatesRequestDto createCoordinatesRequestDto() {
    return new CoordinatesRequestDto(12.12, 42.42);
  }

  private Location createLocation() {
    return new Location(new LocationId(1L),
        10.10, 51.51,
        "street");
  }
}
