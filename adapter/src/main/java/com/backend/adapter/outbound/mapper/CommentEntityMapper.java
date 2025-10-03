package com.backend.adapter.outbound.mapper;

import com.backend.adapter.outbound.entity.CommentEntity;
import com.backend.domain.actor.Comment;
import org.mapstruct.Mapper;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Mapper(componentModel = "spring", uses = {
        HappeningEntityMapper.class,
        ClientEntityMapper.class
})
public interface CommentEntityMapper {

    CommentEntity toCommentEntity(Comment comment);

    Comment toComment(CommentEntity commentEntity);

    default LocalDateTime map(Instant instant) {
        return instant == null ? null : LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    }

    default Instant map(LocalDateTime localDateTime) {
        return localDateTime == null ? null : localDateTime.atZone(ZoneId.systemDefault()).toInstant();
    }
}
