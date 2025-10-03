package com.backend.adapter.outbound.mapper;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {
        HappeningEntityMapper.class,
        ClientEntityMapper.class,
        CommentEntityMapper.class
})
public interface ReactionEntityMapper {
}
