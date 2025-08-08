package com.backend.adapter.in.mapper;

import com.backend.domain.happening.metadata.EventMetadata;
import com.backend.domain.happening.metadata.Metadata;
import com.backend.adapter.in.dto.shared.EventMetadataDto;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

/**
 * MapStruct mapper interface for converting between EventMetadata domain objects and DTOs.
 * <p>
 * Handles bidirectional mapping between EventMetadata domain entities and their
 * corresponding DTOs, including polymorphic metadata conversion.
 * </p>
 *
 * <p>Dependencies: LocationMapper</p>
 * <p>Component Model: Spring</p>
 */
@Mapper(componentModel = "spring", uses = LocationMapper.class)
public interface EventMetadataMapper {
    /**
     * Converts EventMetadataDto to EventMetadata domain object.
     *
     * @param eventMetadataDto The DTO containing event metadata
     * @return EventMetadata domain object
     */
    EventMetadata toDomain(EventMetadataDto eventMetadataDto);

    /**
     * Converts EventMetadata domain object to EventMetadataDto.
     *
     * @param eventMetadata The domain object containing event metadata
     * @return EventMetadataDto for API responses
     */
    EventMetadataDto toDto(EventMetadata eventMetadata);

    /**
     * Polymorphic converter for Metadata to EventMetadataDto.
     * <p>
     * Handles conversion from base Metadata type to specific EventMetadataDto,
     * throwing exception for unsupported metadata types.
     * </p>
     *
     * @param metadata The base metadata object
     * @return EventMetadataDto if the metadata is of EventMetadata type
     * @throws IllegalArgumentException if metadata type is not supported
     */
    @Named("metadataToEventMetadataDto")
    default EventMetadataDto metadataToEventMetadataDto(Metadata metadata) {
        if (metadata instanceof EventMetadata em) {
            return toDto(em);
        }
        throw new IllegalArgumentException("Unsupported metadata type: " + metadata.getClass());
    }
}
