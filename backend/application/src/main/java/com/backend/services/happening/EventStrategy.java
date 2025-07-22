package com.backend.services.happening;

import com.backend.happening.Event;
import com.backend.happening.Happening;
import org.springframework.stereotype.Component;

/**
 * Strategy implementation for updating {@link Event} instances.
 *
 * This class defines how to merge data from an updated {@code Event} into an existing one
 * by copying over mutable fields such as title, description, comments, sentiment engagement,
 * and metadata.
 *
 * Registered as a Spring {@link Component} for dependency injection.
 */
@Component
public class EventStrategy implements HappeningStrategy<Event> {

    /**
     * Updates an existing {@link Event} with values from another {@link Event} instance.
     * All fields are replaced with values from {@code updated}, preserving immutability
     * by returning a new object.
     *
     * @param existing the current {@code Event} instance to update.
     * @param updated the {@code Event} containing new values.
     * @return a new {@code Event} instance with merged state.
     */
    @Override
    public Event update(Event existing, Event updated) {
        return existing.toBuilder()
                .title(updated.title())
                .description(updated.description())
                .comments(updated.comments())
                .sentimentEngagement(updated.sentimentEngagement())
                .metadata(updated.metadata())
                .build();
    }

    /**
     * Returns the supported {@link Happening} type handled by this strategy.
     *
     * @return the {@code Event.class} type token.
     */
    @Override
    public Class<Event> getSupportedType() {
        return Event.class;
    }
}
