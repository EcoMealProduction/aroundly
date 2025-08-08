package com.backend.adapter.in.dto.shared;

import lombok.Builder;
import lombok.NonNull;

import java.time.LocalDateTime;

/**
 * Data Transfer Object containing incident metadata information.
 * <p>
 * Holds contextual information about an incident including who reported it,
 * where it occurred, and temporal details.
 * </p>
 *
 * @param authorUsername  Username of the person who reported the incident
 * @param location        Where the incident occurred
 * @param createdAt       When the incident was reported
 * @param expirationTime  When the incident report expires
 */
@Builder(toBuilder = true)
public record IncidentMetadataDto(
        @NonNull String authorUsername,
        @NonNull LocationDto location,
        @NonNull LocalDateTime createdAt,
        @NonNull LocalDateTime expirationTime) {}
