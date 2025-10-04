package com.backend.adapter.outbound.mapper;

import com.backend.adapter.outbound.entity.EventEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {
        HappeningEntityMapper.class
})
public interface EventEntityMapper {

}
