package com.backend.adapter.in.mapper.request;

import com.backend.adapter.in.dto.request.IncidentMetadataRequestDto;
import com.backend.adapter.in.mapper.MediaRefMapper;
import com.backend.domain.happening.metadata.IncidentMetadata;
import com.backend.domain.happening.metadata.Metadata;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

/**
 * MapStruct mapper for converting between {@link IncidentMetadataRequestDto}
 * received in controller requests and the {@link IncidentMetadata} domain model.
 *
 * Uses {@link LocationRequestMapper} and {@link MediaRefMapper} for nested mapping.
 * Certain fields are ignored during mapping from DTO to domain, as they are set
 * by the application (e.g., timestamps, actor) rather than provided by the client.
 */
@Mapper(componentModel = "spring", uses = {LocationRequestMapper.class, MediaRefMapper.class})
public interface IncidentMetadataRequestMapper {

  /**
   * Maps a domain {@link IncidentMetadata} to a {@link IncidentMetadataRequestDto},
   * typically for echoing submitted values or pre-filling request forms.
   *
   * @param metadata the domain incident metadata
   * @return a request DTO
   */
  IncidentMetadataRequestDto toDto(IncidentMetadata metadata);

  /**
   * Maps a {@link IncidentMetadataRequestDto} from the client into a domain
   * {@link IncidentMetadata}. Ignores {@code expirationTime}, {@code createdAt},
   * and {@code actor}, which are set by the server.
   *
   * @param metadata the client-provided incident metadata DTO
   * @return the mapped domain object
   */
  @Mapping(target = "expirationTime", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "actor", ignore = true)
  IncidentMetadata toDomain(IncidentMetadataRequestDto metadata);
}
