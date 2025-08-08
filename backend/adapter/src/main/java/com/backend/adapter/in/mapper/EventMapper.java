package com.backend.adapter.in.mapper;

import com.backend.domain.happening.Event;
import com.backend.adapter.in.dto.request.EventRequestDto;
import com.backend.adapter.in.dto.response.EventResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * MapStruct mapper interface for converting between Event domain objects and DTOs.
 * <p>
 * Handles bidirectional mapping between Event domain entities and their corresponding
 * request/response DTOs, managing complex field mappings and ignoring fields that
 * require special handling.
 * </p>
 *
 * <p>Dependencies: EventMetadataMapper</p>
 * <p>Component Model: Spring</p>
 */
@Mapper(
        componentModel = "spring",
        uses = {EventMetadataMapper.class}
)
public interface EventMapper {
    /**
     * Converts EventRequestDto to Event domain object.
     * <p>
     * Maps request DTO fields to domain object, ignoring comments and sentiment
     * engagement as these are populated separately during domain processing.
     * </p>
     *
     * @param eventRequestDto The request DTO containing event creation data
     * @return Event domain object ready for business logic processing
     */
    @Mapping(target = "comments", ignore = true)
    @Mapping(target = "sentimentEngagement",  ignore = true)
    @Mapping(source = "eventMetadata", target = "metadata")
    Event toEvent(EventRequestDto eventRequestDto);

    /**
     * Converts Event domain object to EventResponseDto.
     * <p>
     * Maps domain object to response DTO, using qualified mapping for metadata
     * conversion and ignoring fields that are populated separately.
     * </p>
     *
     * @param event The domain object containing complete event data
     * @return EventResponseDto ready for API response
     */
    @Mapping(source = "metadata", target = "eventMetadata", qualifiedByName = "metadataToEventMetadataDto")
    @Mapping(target = "comments", ignore = true)
    @Mapping(target = "sentiment",  ignore = true)
    EventResponseDto toResponseDto(Event event);
}
