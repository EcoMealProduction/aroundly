package com.backend.adapter.outbound.mapper;

import com.backend.adapter.outbound.entity.HappeningEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {
        LocationEntityMapper.class,
        ClientEntityMapper.class
})
public interface HappeningEntityMapper {

}
