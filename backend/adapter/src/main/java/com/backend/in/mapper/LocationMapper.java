package com.backend.in.mapper;

import com.backend.in.dto.shared.LocationDto;
import com.backend.shared.Location;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LocationMapper {
    Location locationDtoToLocation(LocationDto locationDto);
    LocationDto locationToLocationDto(Location location);
}
