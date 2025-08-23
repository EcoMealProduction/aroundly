package com.backend.services.happening;

import com.backend.domain.happening.old.OldHappening;
import com.backend.domain.happening.old.OldIncident;
import org.springframework.stereotype.Component;

/**
 * Strategy implementation for updating {@link OldIncident} instances.
 *
 * Defines the logic for creating a new {@code Incident} by merging updated field values
 * into an existing one, preserving immutability.
 *
 * Registered as a Spring {@link Component} for use in dependency injection and runtime dispatch.
 */
@Component
public class IncidentStrategy implements HappeningStrategy<OldIncident> {

    /**
     * Updates an existing {@link OldIncident} with values from an updated {@link OldIncident}.
     * Replaces all updatable fields: title, description, comments, metadata,
     * sentiment engagement, and incident engagement stats.
     *
     * @param existing the original {@code Incident} to update.
     * @param updated the incoming {@code Incident} containing new values.
     * @return a new {@code Incident} instance with merged data.
     */
    @Override
    public OldIncident update(OldIncident existing, OldIncident updated) {
        return existing.toBuilder()
                .title(updated.title())
                .description(updated.description())
                .comments(updated.comments())
                .metadata(updated.metadata())
                .sentimentEngagement(updated.sentimentEngagement())
                .incidentEngagementStats(updated.engagementStats())
                .build();
    }

    /**
     * Returns the supported {@link OldHappening} subtype handled by this strategy.
     *
     * @return the {@code Incident.class} type.
     */
    @Override
    public Class<OldIncident> getSupportedType() {
        return OldIncident.class;
    }
}
