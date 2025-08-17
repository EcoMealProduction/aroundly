package com.backend.adapter.in.dto.metadata;

import com.backend.adapter.in.dto.shared.LocationDto;
import lombok.Builder;
import lombok.NonNull;

import java.time.LocalDateTime;

/**
 * Data Transfer Object containing event metadata information.
 *
 * Holds contextual information about an event including who created it,
 * where it takes place, and when it occurs.
 *
 * @param authorUsername Username of the event creator
 * @param location       Where the event takes place
 * @param startTime      When the event begins
 * @param endTime        When the event ends
 */
@Builder(toBuilder = true)
public record EventMetadataDto(
        @NonNull String authorUsername,
        @NonNull LocationDto location,
        @NonNull LocalDateTime startTime,
        @NonNull LocalDateTime endTime) {}
