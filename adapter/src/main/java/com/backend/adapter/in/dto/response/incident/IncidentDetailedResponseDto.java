package com.backend.adapter.in.dto.response.incident;

import com.backend.domain.media.Media;
import java.util.Set;
import lombok.Builder;

/**
 * Response DTO carrying detailed information about an incident.
 *
 * @param title              short title of the incident
 * @param description        detailed description of the incident
 * @param actorUsername      username of the user who reported/created the incident
 * @param media              related media (images, videos, etc.)
 * @param confirm            number of confirmations
 * @param deny               number of denials
 * @param consecutiveDenies  consecutive denial count
 * @param like               number of likes
 * @param dislike            number of dislikes
 * @param lat                latitude of the incident location
 * @param lon                longitude of the incident location
 * @param address            human-readable formatted address of the incident
 */
@Builder(toBuilder = true)
public record IncidentDetailedResponseDto(
    String title,
    String description,
    String actorUsername,
    Set<Media> media,
    int confirm,
    int deny,
    int consecutiveDenies,
    int like,
    int dislike,
    double lat,
    double lon,
    String address) { }