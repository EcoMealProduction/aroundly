package com.backend.adapter.in.dto.request;

import lombok.Builder;
import lombok.NonNull;

/**
 * Data Transfer Object for reporting new incidents to the system.
 *
 * Contains all required information to submit an incident report,
 * including description and contextual metadata.
 *
 * @param title             Brief summary or title of the incident
 * @param description       Detailed account of what happened
 * @param metadata  Contextual information including location and timing
 */
@Builder(toBuilder = true)
public record IncidentRequestDto(
        @NonNull String title,
        @NonNull String description,
        @NonNull IncidentMetadataRequestDto metadata) { }
