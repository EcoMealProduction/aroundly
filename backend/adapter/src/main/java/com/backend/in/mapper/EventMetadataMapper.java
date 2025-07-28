package com.backend.in.mapper;

import com.backend.happening.metadata.EventMetadata;
import com.backend.in.dto.shared.EventMetadataDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = LocationMapper.class)
public interface EventMetadataMapper {
    EventMetadata eventMetadataDtoToEventMetadata(EventMetadataDto eventMetadataDto);
    EventMetadataDto eventMetadataToEventMetadataDto(EventMetadata eventMetadata);
}
