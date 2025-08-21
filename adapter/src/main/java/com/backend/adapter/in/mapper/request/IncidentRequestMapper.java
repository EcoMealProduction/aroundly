package com.backend.adapter.in.mapper.request;

import com.backend.adapter.in.dto.metadata.EventMetadataDto;
import com.backend.adapter.in.dto.request.IncidentMetadataRequestDto;
import com.backend.adapter.in.dto.request.IncidentRequestDto;
import com.backend.domain.happening.Incident;
import com.backend.domain.happening.metadata.EventMetadata;
import com.backend.domain.happening.metadata.IncidentMetadata;
import com.backend.domain.happening.metadata.Metadata;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

/**
 * MapStruct mapper for converting between {@link IncidentRequestDto}
 * received in controller requests and the {@link Incident} domain model.
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
   * Maps a {@link IncidentRequestDto} from the client into a domain {@link Incident}.
   * Ignores {@code sentimentEngagement}, {@code incidentEngagementStats}, and {@code comments},
   * which are set by the server. Maps {@code metadata} to {@code metadata}.
   *
   * @param incident the client-provided incident DTO
   * @return the mapped domain object
   */
  @Mapping(target = "sentimentEngagement", ignore = true)
  @Mapping(target = "incidentEngagementStats", ignore = true)
  @Mapping(target = "comments", ignore = true)
  @Mapping(target = "metadata", source = "metadata")
  Incident toDomain(IncidentRequestDto incident);
}
