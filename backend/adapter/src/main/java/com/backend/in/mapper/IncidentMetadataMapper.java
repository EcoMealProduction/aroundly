package com.backend.in.mapper;

import com.backend.happening.metadata.IncidentMetadata;
import com.backend.in.dto.shared.IncidentMetadataDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = LocationMapper.class)
public interface IncidentMetadataMapper {
    IncidentMetadata incidentMetadataDtoToIncidentMetadata(IncidentMetadataDto incidentMetadataDto);
    IncidentMetadataDto incidentMetadataToIncidentMetadataDto(IncidentMetadata incidentMetadata);
}
