package com.backend.adapter.in.dto.shared;

import lombok.Builder;

/**
 * Data Transfer Object containing incident engagement statistics.
 * <p>
 * Tracks how users interact with incident reports through
 * confirmations, denials, and engagement patterns.
 * </p>
 *
 * @param confirms          Number of users who confirmed this incident
 * @param denies            Number of users who denied this incident
 * @param consecutiveDenies Number of consecutive denials received
 */
@Builder(toBuilder = true)
public record IncidentEngagementStatsDto(
        int confirms,
        int denies,
        int consecutiveDenies) {}
