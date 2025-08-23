package com.backend.adapter.in.mapper.request;

import com.backend.adapter.in.dto.request.LocationRequestDto;
import com.backend.domain.old.OldLocation;
import org.mapstruct.Mapper;

/**
 * MapStruct mapper for converting between {@link LocationRequestDto}
 * received in controller requests and the {@link OldLocation} domain model.
 *
 * Used to translate client-provided location data into domain objects
 * for use within the application layer.
 */
@Mapper(componentModel = "spring")
public interface LocationRequestMapper {

  /**
   * Maps a {@link LocationRequestDto} from the client into a domain {@link OldLocation}
   * for use by controllers and application logic.
   *
   * @param location the client-provided request DTO
   * @return the mapped domain object
   */
  OldLocation toDomain(LocationRequestDto location);
}
