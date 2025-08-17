package com.backend.adapter.in.mapper.request;

import com.backend.adapter.in.dto.request.LocationRequestDto;
import com.backend.domain.shared.Location;
import org.mapstruct.Mapper;

/**
 * MapStruct mapper for converting between {@link LocationRequestDto}
 * received in controller requests and the {@link Location} domain model.
 *
 * Used to translate client-provided location data into domain objects
 * for use within the application layer.
 */
@Mapper(componentModel = "spring")
public interface LocationRequestMapper {

  /**
   * Maps a {@link LocationRequestDto} from the client into a domain {@link Location}
   * for use by controllers and application logic.
   *
   * @param location the client-provided request DTO
   * @return the mapped domain object
   */
  Location toDomain(LocationRequestDto location);
}
