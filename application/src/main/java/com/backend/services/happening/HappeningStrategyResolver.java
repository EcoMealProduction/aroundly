package com.backend.services.happening;

import com.backend.domain.happening.old.Event;
import com.backend.domain.happening.old.OldHappening;
import com.backend.domain.happening.old.OldIncident;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Central resolver for {@link HappeningStrategy} implementations.
 *
 * This class maps {@link OldHappening} subtypes (e.g., {@link Event}, {@link OldIncident}) to their
 * corresponding strategy implementations and delegates update operations accordingly.
 *
 * Registered as a Spring {@link Component} for automatic injection and discovery.
 */
@Component
public class HappeningStrategyResolver {

    /**
     * Internal registry mapping supported {@link OldHappening} classes to their corresponding strategies.
     */
    private final Map<Class<? extends OldHappening>, HappeningStrategy<? extends OldHappening>> strategies = new HashMap<>();

    /**
     * Constructs the resolver with all discovered {@link HappeningStrategy} beans.
     *
     * @param strategyList the list of available strategy implementations injected by Spring.
     */
    public HappeningStrategyResolver(List<HappeningStrategy<? extends OldHappening>> strategyList) {
        for (HappeningStrategy<? extends OldHappening> strategy : strategyList) {
            strategies.put(strategy.getSupportedType(), strategy);
        }
    }

    /**
     * Resolves and delegates the update operation to the appropriate {@link HappeningStrategy}
     * based on the runtime class of the {@code existing} happening.
     *
     * @param existing the current happening instance to be updated.
     * @param updated the new instance containing the updated state.
     * @param <T> the type of {@link OldHappening}.
     * @return a new {@link OldHappening} instance with applied updates.
     * @throws IllegalArgumentException if no strategy is found for the type.
     */
    @SuppressWarnings("unchecked")
    public <T extends OldHappening> OldHappening update(T existing, T updated) {
        HappeningStrategy<T> strategy = (HappeningStrategy<T>) strategies.get(existing.getClass());
        if (strategy == null) throw new IllegalArgumentException("No strategy for type: " + existing.getClass());

        return strategy.update(existing, updated);
    }
}
