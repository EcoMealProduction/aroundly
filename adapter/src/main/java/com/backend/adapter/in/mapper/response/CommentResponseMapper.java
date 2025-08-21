package com.backend.adapter.in.mapper.response;

import com.backend.adapter.in.dto.response.CommentResponseDto;
import com.backend.domain.user.Comment;
import org.mapstruct.Mapper;

/**
 * MapStruct mapper for converting the domain {@link Comment} entity
 * into its corresponding {@link CommentResponseDto} representation.
 *
 * This mapper is used for preparing comment data to be sent
 * back to clients in API responses.
 */
@Mapper(componentModel = "spring")
public interface CommentResponseMapper {

  /**
   * Maps a domain {@link Comment} to a {@link CommentResponseDto}
   * for use in API responses.
   *
   * @param comment the domain comment
   * @return the response DTO
   */
  CommentResponseDto toDto(Comment comment);
}
