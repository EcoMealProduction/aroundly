package com.backend.adapter.in.mapper;

import com.backend.adapter.in.dto.media.MediaRefDto;
import com.backend.domain.happening.media.MediaRef;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * MapStruct mapper for converting between {@link MediaRefDto}
 * received in controller requests and the {@link MediaRef} domain model.
 *
 * Used to translate client-provided media references into domain objects
 * for use within the application layer.
 */
@Mapper(componentModel = "spring")
public interface MediaRefMapper {

  /**
   * Maps a domain {@link MediaRef} to a {@link MediaRefDto},
   * typically for echoing submitted values or preparing request payloads.
   *
   * @param media the domain media reference
   * @return a media reference DTO
   */
  MediaRefDto toDto(MediaRef media);

  /**
   * Maps a {@link MediaRefDto} from the client into a domain {@link MediaRef}.
   * Ignores {@code kind} and {@code contentType} fields, which are set by the server.
   *
   * @param media the client-provided media reference DTO
   * @return the mapped domain object
   */
  @Mapping(target = "kind", ignore = true)
  @Mapping(target = "contentType", ignore = true)
  MediaRef toDomain(MediaRefDto media);
}
