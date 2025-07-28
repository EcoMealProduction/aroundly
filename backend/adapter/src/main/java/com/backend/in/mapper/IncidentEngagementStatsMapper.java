package com.backend.in.mapper;

import com.backend.happening.IncidentEngagementStats;
import com.backend.in.dto.shared.IncidentEngagementStatsDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IncidentEngagementStatsMapper {
    IncidentEngagementStats toDomain(IncidentEngagementStatsDto dto);
    IncidentEngagementStatsDto toDto(IncidentEngagementStats stats);
}
