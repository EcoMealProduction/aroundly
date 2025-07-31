package com.backend.in.dto.request;

import com.backend.in.dto.shared.IncidentMetadataDto;
import lombok.Builder;
import lombok.NonNull;

/**
 * Data Transfer Object for reporting new incidents to the system.
 * <p>
 * Contains all required information to submit an incident report,
 * including description and contextual metadata.
 * </p>
 *
 * @param title             Brief summary or title of the incident
 * @param description       Detailed account of what happened
 * @param incidentMetadata  Contextual information including location and timing
 */
@Builder(toBuilder = true)
public record IncidentRequestDto(
        @NonNull String title,
        @NonNull String description,
        @NonNull IncidentMetadataDto incidentMetadata) {}
