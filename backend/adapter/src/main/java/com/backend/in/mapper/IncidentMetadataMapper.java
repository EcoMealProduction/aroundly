package com.backend.in.mapper;

import com.backend.happening.metadata.IncidentMetadata;
import com.backend.happening.metadata.Metadata;
import com.backend.in.dto.shared.IncidentMetadataDto;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

/**
 * MapStruct mapper interface for converting between IncidentMetadata domain objects and DTOs.
 * <p>
 * Handles bidirectional mapping between IncidentMetadata domain entities and their
 * corresponding DTOs, including polymorphic metadata conversion.
 * </p>
 *
 * <p>Dependencies: LocationMapper</p>
 * <p>Component Model: Spring</p>
 */
@Mapper(componentModel = "spring", uses = LocationMapper.class)
public interface IncidentMetadataMapper {
    /**
     * Converts IncidentMetadataDto to IncidentMetadata domain object.
     *
     * @param incidentMetadataDto The DTO containing incident metadata
     * @return IncidentMetadata domain object
     */
    IncidentMetadata toDomain(IncidentMetadataDto incidentMetadataDto);

    /**
     * Converts IncidentMetadata domain object to IncidentMetadataDto.
     *
     * @param incidentMetadata The domain object containing incident metadata
     * @return IncidentMetadataDto for API responses
     */
    IncidentMetadataDto toDto(IncidentMetadata incidentMetadata);

    /**
     * Polymorphic converter for Metadata to IncidentMetadataDto.
     * <p>
     * Handles conversion from base Metadata type to specific IncidentMetadataDto,
     * throwing exception for unsupported metadata types.
     * </p>
     *
     * @param metadata The base metadata object
     * @return IncidentMetadataDto if the metadata is of IncidentMetadata type
     * @throws IllegalArgumentException if metadata type is not supported
     */
    @Named("metadataToIncidentMetadataDto")
    default IncidentMetadataDto metadataToIncidentMetadataDto(Metadata metadata) {
        if (metadata instanceof IncidentMetadata em) {
            return toDto(em);
        }
        throw new IllegalArgumentException("Unsupported metadata type: " + metadata.getClass());
    }
}
