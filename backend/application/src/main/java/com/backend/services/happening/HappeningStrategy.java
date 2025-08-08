package com.backend.services.happening;

import com.backend.domain.happening.Event;
import com.backend.domain.happening.Happening;
import com.backend.domain.happening.Incident;

/**
 * Strategy interface for handling operations on specific {@link Happening} types,
 * such as {@link Event} or {@link Incident}.
 *
 * Implementations of this interface encapsulate logic for updating and identifying
 * supported types of {@code Happening} objects.
 *
 * @param <T> The specific subtype of {@link Happening} this strategy supports.
 */
public interface HappeningStrategy<T extends Happening> {

    /**
     * Defines how an existing {@link Happening} instance should be updated
     * with data from a new one.
     *
     * @param existing the original instance to be updated.
     * @param updated the incoming instance with new data.
     * @return a new instance of type {@code T} that reflects the applied update.
     */
    T update(T existing, T updated);

    /**
     * Returns the class object representing the {@link Happening} subtype
     * that this strategy is designed to handle.
     *
     * @return the {@link Class} object for the supported type.
     */
    Class<T> getSupportedType();
}
