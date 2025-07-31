package com.backend.in.mapper;

import com.backend.happening.Incident;
import com.backend.in.dto.request.IncidentRequestDto;
import com.backend.in.dto.response.IncidentResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * MapStruct mapper interface for converting between Incident domain objects and DTOs.
 * <p>
 * Handles bidirectional mapping between Incident domain entities and their corresponding
 * request/response DTOs, managing complex field mappings and engagement statistics.
 * </p>
 *
 * <p>Dependencies: IncidentEngagementStatsMapper, IncidentMetadataMapper</p>
 * <p>Component Model: Spring</p>
 */
@Mapper(
        componentModel = "spring",
        uses = {
                IncidentEngagementStatsMapper.class,
                IncidentMetadataMapper.class
        }
)
public interface IncidentMapper {
    /**
     * Converts IncidentRequestDto to Incident domain object.
     * <p>
     * Maps request DTO fields to domain object, ignoring fields that are
     * populated during business logic processing.
     * </p>
     *
     * @param dto The request DTO containing incident creation data
     * @return Incident domain object ready for business logic processing
     */
    @Mapping(source = "incidentMetadata", target = "metadata")
    @Mapping(target = "comments",  ignore = true)
    @Mapping(target = "sentimentEngagement",  ignore = true)
    @Mapping(target = "incidentEngagementStats",  ignore = true)
    Incident toIncident(IncidentRequestDto dto);

    /**
     * Converts Incident domain object to IncidentResponseDto.
     * <p>
     * Maps domain object to response DTO, using qualified mapping for metadata
     * conversion and including engagement statistics.
     * </p>
     *
     * @param incident The domain object containing complete incident data
     * @return IncidentResponseDto ready for API response
     */
    @Mapping(source = "metadata", target = "incidentMetadata", qualifiedByName = "metadataToIncidentMetadataDto")
    @Mapping(target = "comments",   ignore = true)
    @Mapping(target = "sentiment",  ignore = true)
    @Mapping(source = "incidentEngagementStats", target = "engagementStats")
    IncidentResponseDto toResponseDto(Incident incident);
}
