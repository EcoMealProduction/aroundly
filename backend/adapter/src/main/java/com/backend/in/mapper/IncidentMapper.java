package com.backend.in.mapper;

import com.backend.happening.Incident;
import com.backend.in.dto.request.IncidentRequestDto;
import com.backend.in.dto.response.IncidentResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        componentModel = "spring",
        uses = {
                IncidentEngagementStatsMapper.class,
                IncidentMetadataMapper.class
        }
)
public interface IncidentMapper {
    @Mapping(source = "incidentMetadata", target = "metadata")
    @Mapping(target = "comments",  ignore = true)
    @Mapping(target = "sentimentEngagement",  ignore = true)
    @Mapping(target = "incidentEngagementStats",  ignore = true)
    Incident incidentRequestDtoToIncident(IncidentRequestDto dto);

    @Mapping(source = "metadata", target = "incidentMetadata")
    @Mapping(target = "comments",   ignore = true)
    @Mapping(target = "sentiment",  ignore = true)
    @Mapping(source = "incidentEngagementStats", target = "engagementStats")
    IncidentResponseDto incidentToIncidentResponseDto(Incident incident);
}
