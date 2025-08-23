package com.backend.adapter.in.mapper.response;

import com.backend.adapter.in.dto.response.incident.IncidentPreviewResponseDto;
import com.backend.domain.happening.old.OldIncident;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

/**
 * MapStruct mapper for converting the domain {@link OldIncident} entity
 * into a preview {@link IncidentPreviewResponseDto} representation.
 *
 * Uses {@link IncidentMetadataResponseMapper} to handle the mapping
 * of incident metadata, including nested location and media details.
 * The mapping process enforces full coverage of target properties by
 * setting {@code unmappedTargetPolicy} to {@code ERROR}.
 */
@Mapper(componentModel = "spring", uses = IncidentMetadataResponseMapper.class,
    unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface IncidentPreviewResponseMapper {

  /**
   * Maps a domain {@link OldIncident} to a {@link IncidentPreviewResponseDto}.
   * Maps metadata using the qualified method {@code metadataToIncidentMetadataDto}
   * from {@link IncidentMetadataResponseMapper}.
   *
   * @param oldIncident the domain incident
   * @return the preview response DTO
   */
  @Mapping(target = "metadata", source = "metadata", qualifiedByName = "metadataToIncidentMetadataDto")
  IncidentPreviewResponseDto toDto(OldIncident oldIncident);
}
