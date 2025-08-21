package com.backend.adapter.in.mapper.response;

import com.backend.adapter.in.dto.response.incident.IncidentDetailedResponseDto;
import com.backend.domain.happening.Incident;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * MapStruct mapper for converting the domain {@link Incident} entity
 * into a detailed {@link IncidentDetailedResponseDto} representation.
 *
 * Uses {@link IncidentMetadataResponseMapper}, {@link CommentResponseMapper},
 * and {@link SentimentEngagementResponseMapper} for mapping nested objects.
 * Custom field mappings are applied to align domain model properties with
 * the response DTO structure.
 */
@Mapper(componentModel = "spring", uses = {
    IncidentMetadataResponseMapper.class,
    CommentResponseMapper.class,
    SentimentEngagementResponseMapper.class
})
public interface IncidentDetailedResponseMapper {

  /**
   * Maps a domain {@link Incident} to a {@link IncidentDetailedResponseDto}.
   * Maps sentimentEngagement to reaction in the DTO.
   * Maps metadata using a qualified method named metadataToIncidentMetadataDto
   * from the metadata mapper.
   *
   * @param incident the domain incident
   * @return the detailed response DTO
   */
  @Mapping(target = "reaction", source = "sentimentEngagement")
  @Mapping(target = "metadata", source = "metadata", qualifiedByName = "metadataToIncidentMetadataDto")
  IncidentDetailedResponseDto toDto(Incident incident);
}
