package com.backend.adapter.outbound.mapper;

import com.backend.adapter.outbound.entity.ClientEntity;
import com.backend.domain.actor.Actor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ClientEntityMapper {

    ClientEntity toClientEntity(Actor actor);

    Actor toActor(ClientEntity clientEntity);
}
