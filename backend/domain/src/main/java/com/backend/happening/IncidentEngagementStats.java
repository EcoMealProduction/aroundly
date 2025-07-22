package com.backend.happening;

import lombok.Builder;

@Builder(toBuilder = true)
public record IncidentEngagementStats(
        int confirms,
        int denies,
        int consecutiveDenies) {

    public IncidentEngagementStats {

        if (confirms < 0 || denies < 0 || consecutiveDenies < 0)
            throw new IllegalArgumentException("Negative values not allowed.");
    }
}
