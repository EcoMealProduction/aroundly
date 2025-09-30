package com.backend.adapter.outbound.mapper;

import com.backend.adapter.outbound.entity.LocationEntity;
import com.backend.domain.location.Location;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LocationEntityMapper {

    LocationEntity toLocationEntity(Location location);

    Location toLocation(LocationEntity locationEntity);
}
