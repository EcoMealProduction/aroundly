package com.backend.services.happening;

import com.backend.happening.Event;
import com.backend.happening.Happening;
import com.backend.happening.Incident;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Central resolver for {@link HappeningStrategy} implementations.
 *
 * This class maps {@link Happening} subtypes (e.g., {@link Event}, {@link Incident}) to their
 * corresponding strategy implementations and delegates update operations accordingly.
 *
 * Registered as a Spring {@link Component} for automatic injection and discovery.
 */
@Component
public class HappeningStrategyResolver {

    /**
     * Internal registry mapping supported {@link Happening} classes to their corresponding strategies.
     */
    private final Map<Class<? extends Happening>, HappeningStrategy<? extends Happening>> strategies = new HashMap<>();

    /**
     * Constructs the resolver with all discovered {@link HappeningStrategy} beans.
     *
     * @param strategyList the list of available strategy implementations injected by Spring.
     */
    public HappeningStrategyResolver(List<HappeningStrategy<? extends Happening>> strategyList) {
        for (HappeningStrategy<? extends Happening> strategy : strategyList) {
            strategies.put(strategy.getSupportedType(), strategy);
        }
    }

    /**
     * Resolves and delegates the update operation to the appropriate {@link HappeningStrategy}
     * based on the runtime class of the {@code existing} happening.
     *
     * @param existing the current happening instance to be updated.
     * @param updated the new instance containing the updated state.
     * @param <T> the type of {@link Happening}.
     * @return a new {@link Happening} instance with applied updates.
     * @throws IllegalArgumentException if no strategy is found for the type.
     */
    @SuppressWarnings("unchecked")
    public <T extends Happening> Happening update(T existing, T updated) {
        HappeningStrategy<T> strategy = (HappeningStrategy<T>) strategies.get(existing.getClass());
        if (strategy == null) throw new IllegalArgumentException("No strategy for type: " + existing.getClass());

        return strategy.update(existing, updated);
    }
}
