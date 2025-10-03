package com.backend.adapter.outbound.mapper;

import com.backend.adapter.outbound.entity.LocationEntity;
import com.backend.domain.location.Location;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LocationEntityMapper {

//    @Mapping(source = "id.value", target = "id")
    @Mapping(source = "latitude", target = "lat")
    @Mapping(source = "longitude", target = "lng")
    @Mapping(source = "address", target = "addressText")
    LocationEntity toLocationEntity(Location location);

//    @Mapping(source = "id", target = "id.value")
    @Mapping(source = "lat", target = "latitude")
    @Mapping(source = "lng", target = "longitude")
    @Mapping(source = "addressText", target = "address")
    Location toLocation(LocationEntity locationEntity);
}
