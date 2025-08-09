package com.backend.adapter.in.mapper;

import com.backend.domain.happening.IncidentEngagementStats;
import com.backend.adapter.in.dto.shared.IncidentEngagementStatsDto;
import org.mapstruct.Mapper;

/**
 * MapStruct mapper interface for converting between IncidentEngagementStats domain objects and DTOs.
 * <p>
 * Handles bidirectional mapping between IncidentEngagementStats domain entities and their
 * corresponding DTOs for tracking user engagement with incidents.
 * </p>
 *
 * <p>Component Model: Spring</p>
 */
@Mapper(componentModel = "spring")
public interface IncidentEngagementStatsMapper {
    /**
     * Converts IncidentEngagementStatsDto to IncidentEngagementStats domain object.
     *
     * @param dto The DTO containing engagement statistics
     * @return IncidentEngagementStats domain object
     */
    IncidentEngagementStats toDomain(IncidentEngagementStatsDto dto);

    /**
     * Converts IncidentEngagementStats domain object to IncidentEngagementStatsDto.
     *
     * @param stats The domain object containing engagement statistics
     * @return IncidentEngagementStatsDto for API responses
     */
    IncidentEngagementStatsDto toDto(IncidentEngagementStats stats);
}
