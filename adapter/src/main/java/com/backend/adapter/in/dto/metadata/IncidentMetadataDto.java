package com.backend.adapter.in.dto.metadata;

import com.backend.adapter.in.dto.media.MediaRefDto;
import com.backend.adapter.in.dto.shared.LocationDto;
import com.backend.adapter.in.dto.user.ActorDto;
import java.util.Set;
import lombok.Builder;
import lombok.NonNull;

import java.time.LocalDateTime;

/**
 * Data Transfer Object containing incident metadata information.

 * Holds contextual information about an incident including who reported it,
 * where it occurred, and temporal details.
 *
 * @param username        The person who reported the incident
 * @param location        Where the incident occurred
 * @param createdAt       When the incident was reported
 * @param expirationTime  When the incident report expires
 */
@Builder(toBuilder = true)
public record IncidentMetadataDto(
    @NonNull String username,
    @NonNull Set<MediaRefDto> media,
    @NonNull LocationDto location,
    LocalDateTime createdAt,
    LocalDateTime expirationTime) {

    public IncidentMetadataDto {
      if (createdAt == null) createdAt = LocalDateTime.now();
      if (expirationTime == null) expirationTime = createdAt.plusMinutes(30);
    }
}
