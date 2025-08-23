package com.backend.domain.reactions;

import com.backend.domain.happening.old.OldIncident;
import lombok.Builder;

/**
 * Represents engagement statistics for an {@link OldIncident}, including
 * confirmation and denial counts from users.
 */
@Builder(toBuilder = true)
public record EngagementStats(
        int confirms,
        int denies,
        int consecutiveDenies) {

    /**
     * Constructs an {@code IncidentEngagementStats} record with validation.
     * Ensures that no engagement metric is negative.
     *
     * @throws IllegalArgumentException if any of the fields are negative.
     */
    public EngagementStats {
        if (confirms < 0 || denies < 0 || consecutiveDenies < 0)
            throw new IllegalArgumentException("Negative values not allowed.");
    }

    public EngagementStats addConfirm() {
        return toBuilder()
            .confirms(confirms + 1)
            .consecutiveDenies(0)
            .build();
    }

    public EngagementStats addDeny() {
        return toBuilder()
            .denies(denies + 1)
            .consecutiveDenies(consecutiveDenies + 1)
            .build();
    }

}
