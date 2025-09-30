package com.backend.adapter.outbound.mapper;

import com.backend.adapter.outbound.entity.CommentEntity;
import com.backend.domain.actor.Comment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {
        HappeningEntityMapper.class,
        ClientEntityMapper.class
})
public interface CommentEntityMapper {

    CommentEntity toCommentEntity(Comment comment);

    Comment toComment(CommentEntity commentEntity);
}
