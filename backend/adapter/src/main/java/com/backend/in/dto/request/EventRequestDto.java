package com.backend.in.dto.request;

import com.backend.in.dto.shared.EventMetadataDto;
import lombok.Builder;
import lombok.NonNull;

/**
 * Data Transfer Object for creating new events in the system.
 * <p>
 * Contains all necessary information required to create an event,
 * including basic details and associated metadata.
 * </p>
 *
 * @param title         The name or title of the event
 * @param description   Detailed description of what the event is about
 * @param eventMetadata Additional context information including location and timing
 */
@Builder(toBuilder = true)
public record EventRequestDto(
        @NonNull String title,
        @NonNull String description,
        @NonNull EventMetadataDto eventMetadata) {}
