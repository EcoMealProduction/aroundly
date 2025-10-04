package com.backend.adapter.inbound.mapper;

import com.backend.adapter.inbound.dto.media.MediaDto;
import com.backend.domain.media.Media;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * MapStruct mapper for converting between {@link MediaDto}
 * received in controller requests and the {@link Media} domain model.
 *
 * Used to translate client-provided media references into domain objects
 * for use within the application layer.
 */
@Mapper(componentModel = "spring")
public interface MediaMapper {

  /**
   * Maps a domain {@link Media} to a {@link MediaDto},
   * typically for echoing submitted values or preparing request payloads.
   *
   * @param media the domain media reference
   * @return a media reference DTO
   */
  MediaDto toDto(Media media);

  /**
   * Maps a {@link MediaDto} from the client into a domain {@link Media}.
   * Ignores {@code kind} and {@code contentType} fields, which are set by the server.
   *
   * @param media the client-provided media reference DTO
   * @return the mapped domain object
   */
  @Mapping(target = "size", ignore = true)
  @Mapping(target = "contentType", ignore = true)
  Media toDomain(MediaDto media);
}
