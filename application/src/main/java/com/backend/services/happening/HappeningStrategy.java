package com.backend.services.happening;

import com.backend.domain.happening.old.Event;
import com.backend.domain.happening.old.OldHappening;
import com.backend.domain.happening.old.OldIncident;

/**
 * Strategy interface for handling operations on specific {@link OldHappening} types,
 * such as {@link Event} or {@link OldIncident}.
 *
 * Implementations of this interface encapsulate logic for updating and identifying
 * supported types of {@code Happening} objects.
 *
 * @param <T> The specific subtype of {@link OldHappening} this strategy supports.
 */
public interface HappeningStrategy<T extends OldHappening> {

    /**
     * Defines how an existing {@link OldHappening} instance should be updated
     * with data from a new one.
     *
     * @param existing the original instance to be updated.
     * @param updated the incoming instance with new data.
     * @return a new instance of type {@code T} that reflects the applied update.
     */
    T update(T existing, T updated);

    /**
     * Returns the class object representing the {@link OldHappening} subtype
     * that this strategy is designed to handle.
     *
     * @return the {@link Class} object for the supported type.
     */
    Class<T> getSupportedType();
}
