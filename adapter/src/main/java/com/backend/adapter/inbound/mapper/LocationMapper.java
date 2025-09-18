package com.backend.adapter.inbound.mapper;

import com.backend.adapter.inbound.dto.request.CoordinatesRequestDto;
import com.backend.adapter.inbound.dto.request.RadiusRequestDto;
import com.backend.adapter.inbound.dto.response.CoordinateResponseDto;
import com.backend.domain.location.Location;
import com.backend.port.inbound.commands.CoordinatesCommand;
import com.backend.port.inbound.commands.RadiusCommand;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * MapStruct mapper for converting between location-related
 * domain objects, DTOs, and commands.
 */
@Mapper(componentModel = "spring")
public interface LocationMapper {

  /**
   * Maps a request DTO to a radius command.
   *
   * @param radiusRequestDto the request DTO containing radius parameters
   * @return a command object for radius queries
   */
  RadiusCommand toRadiusCommand(RadiusRequestDto radiusRequestDto);

  /**
   * Maps a request DTO to a coordinates command.
   *
   * @param coordinatesRequestDto the request DTO containing coordinates
   * @return a command object for coordinate queries
   */
  CoordinatesCommand toCoordinatesCommand(CoordinatesRequestDto coordinatesRequestDto);

  /**
   * Maps a domain {@link Location} to a response DTO with latitude,
   * longitude, and address information.
   *
   * @param location the location entity
   * @return a coordinate response DTO
   */
  @Mapping(target = "lon", source = "longitude")
  @Mapping(target = "lat", source = "latitude")
  CoordinateResponseDto toCoordinateResponseDto(Location location);
}
