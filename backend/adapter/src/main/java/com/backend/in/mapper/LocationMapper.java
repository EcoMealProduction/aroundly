package com.backend.in.mapper;

import com.backend.in.dto.shared.LocationDto;
import com.backend.shared.Location;
import org.mapstruct.Mapper;

/**
 * MapStruct mapper interface for converting between Location domain objects and DTOs.
 * <p>
 * Handles bidirectional mapping between Location domain entities and their
 * corresponding DTOs for geographical location data.
 * </p>
 *
 * <p>Component Model: Spring</p>
 */
@Mapper(componentModel = "spring")
public interface LocationMapper {
    /**
     * Converts LocationDto to Location domain object.
     *
     * @param locationDto The DTO containing location data
     * @return Location domain object
     */
    Location toDomain(LocationDto locationDto);

    /**
     * Converts Location domain object to LocationDto.
     *
     * @param location The domain object containing location data
     * @return LocationDto for API responses
     */
    LocationDto toDto(Location location);
}
