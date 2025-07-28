package com.backend.in.mapper;

import com.backend.happening.Event;
import com.backend.in.dto.request.EventRequestDto;
import com.backend.in.dto.response.EventResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        componentModel = "spring",
        uses = {EventMetadataMapper.class}
)
public interface EventMapper {
    @Mapping(target = "comments", ignore = true)
    @Mapping(target = "sentimentEngagement",  ignore = true)
    @Mapping(source = "eventMetadata", target = "metadata")
    Event eventRequestDtoToEvent(EventRequestDto eventRequestDto);

    @Mapping(source = "metadata", target = "eventMetadata")
    @Mapping(target = "comments", ignore = true)
    @Mapping(target = "sentiment",  ignore = true)
    EventResponseDto eventToEventResponseDto(Event event);
}
