package com.backend.adapter.in.mapper.response;

import com.backend.adapter.in.dto.request.IncidentMetadataRequestDto;
import com.backend.adapter.in.dto.response.incident.IncidentMetadataResponseDto;
import com.backend.adapter.in.mapper.MediaRefMapper;
import com.backend.domain.happening.metadata.IncidentMetadata;
import com.backend.domain.happening.metadata.Metadata;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

/**
 * MapStruct mapper for converting the domain {@link IncidentMetadata} entity
 * into its corresponding {@link IncidentMetadataResponseDto} representation.
 *
 * Uses {@link LocationResponseMapper} and {@link MediaRefMapper} to handle
 * nested property mappings for location and media references.
 */
@Mapper(componentModel = "spring", uses = {
  LocationResponseMapper.class,
  MediaRefMapper.class})
public interface IncidentMetadataResponseMapper {

  /**
   * Maps a domain {@link IncidentMetadata} to a {@link IncidentMetadataResponseDto}.
   * Maps the actor's username from {@code actor.username} in the domain object
   * to the {@code username} field in the DTO.
   *
   * @param metadata the domain incident metadata
   * @return the response DTO
   */
  @Mapping(target = "username", source = "actor.username")
  IncidentMetadataResponseDto toDto(IncidentMetadata metadata);

  /**
   * Maps a generic {@link Metadata} instance to a {@link IncidentMetadataResponseDto}
   * if it is of type {@link IncidentMetadata}. This method is intended for use
   * in qualified mappings where the metadata field type may be declared as
   * the base {@link Metadata}.
   *
   * @param metadata the metadata instance to map
   * @return the mapped response DTO
   * @throws IllegalArgumentException if the metadata is not an IncidentMetadata
   */
  @Named("metadataToIncidentMetadataDto")
  default IncidentMetadataResponseDto metadataToIncidentMetadataResponseDto(Metadata metadata) {
    if (metadata instanceof IncidentMetadata im) {
      return toDto(im);
    }
    throw new IllegalArgumentException("Unsupported metadata type: " + metadata.getClass());
  }
}
