package com.backend.adapter.in.mapper.response;

import com.backend.adapter.in.dto.response.LocationResponseDto;
import com.backend.domain.shared.Location;
import org.mapstruct.Mapper;

/**
 * MapStruct mapper for converting the domain {@link Location} entity
 * into its corresponding {@link LocationResponseDto} representation.
 *
 * This mapper is used to translate location details from the domain
 * model into a format suitable for returning in API responses.
 */
@Mapper(componentModel = "spring")
public interface LocationResponseMapper {

  /**
   * Maps a domain {@link Location} to a {@link LocationResponseDto}
   * for use in API responses.
   *
   * @param location the domain location
   * @return the response DTO
   */
  LocationResponseDto toDto(Location location);
}
