package com.backend.in.dto.shared;

import lombok.Builder;

@Builder(toBuilder = true)
public record IncidentEngagementStatsDto(
        int confirms,
        int denies,
        int consecutiveDenies
) {}
