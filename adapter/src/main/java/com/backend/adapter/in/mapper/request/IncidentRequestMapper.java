package com.backend.adapter.in.mapper.request;

import com.backend.adapter.in.dto.request.IncidentRequestDto;
import com.backend.domain.happening.old.OldIncident;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

/**
 * MapStruct mapper for converting between {@link IncidentRequestDto}
 * received in controller requests and the {@link OldIncident} domain model.
 *
 * Uses {@link IncidentMetadataRequestMapper} for nested mapping of incident metadata.
 * Certain target fields are ignored when mapping from DTO to domain, as they are
 * populated by the application (e.g., engagement stats, comments) rather than provided
 * by the client.
 */
@Mapper(componentModel = "spring", uses = IncidentMetadataRequestMapper.class,
    unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface IncidentRequestMapper {

  /**
   * Maps a {@link IncidentRequestDto} from the client into a domain {@link OldIncident}.
   * Ignores {@code sentimentEngagement}, {@code incidentEngagementStats}, and {@code comments},
   * which are set by the server. Maps {@code metadata} to {@code metadata}.
   *
   * @param incident the client-provided incident DTO
   * @return the mapped domain object
   */
  @Mapping(target = "sentimentEngagement", ignore = true)
  @Mapping(target = "engagementStats", ignore = true)
  @Mapping(target = "comments", ignore = true)
  @Mapping(target = "metadata", source = "metadata")
  OldIncident toDomain(IncidentRequestDto incident);
}
