package com.backend.adapter.in.dto.request;

import com.backend.adapter.in.dto.media.MediaRefDto;

import java.util.Set;
import lombok.Builder;

/**
 * DTO for submitting metadata related to an incident.
 *
 * Contains a reference to associated media and the location
 * where the incident occurred.
 *
 */
@Builder(toBuilder = true)
public record IncidentMetadataRequestDto(
    Set<MediaRefDto> media,
    LocationRequestDto location) { }
