package com.backend.services.happening;

import com.backend.happening.Event;
import com.backend.happening.Happening;
import com.backend.happening.Incident;
import com.backend.port.in.HappeningUseCase;
import com.backend.port.out.HappeningRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service implementation for handling operations on {@link Happening} entities.
 *
 * Delegates persistence to the {@link HappeningRepository}, and type-specific update logic
 * to the {@link HappeningStrategyResolver}. This service supports operations for both
 * {@link Event} and {@link Incident} instances.
 *
 * Registered as a Spring {@link Service} for business logic orchestration.
 */
@Service
public class HappeningService implements HappeningUseCase {

    private final HappeningRepository happeningRepository;
    private final HappeningStrategyResolver happeningStrategyResolver;

    /**
     * Constructs the {@code HappeningService} with required dependencies.
     *
     * @param happeningRepository repository for persistence operations.
     * @param happeningStrategyResolver strategy resolver for polymorphic update handling.
     */
    @Autowired
    public HappeningService(HappeningRepository happeningRepository, HappeningStrategyResolver happeningStrategyResolver) {
        this.happeningRepository = happeningRepository;
        this.happeningStrategyResolver = happeningStrategyResolver;
    }

    /**
     * Retrieves all {@link Happening} entries within a given visibility range.
     *
     * @param range visibility range in meters (currently unused in method body).
     * @return list of matching {@code Happening} instances.
     */
    @Override
    public List<Happening> findAllInGivenRange(int range) {
        return happeningRepository.findByAllInGivenRange(range);
    }

    /**
     * Retrieves a specific {@link Happening} by its ID.
     *
     * @param happeningId the unique identifier.
     * @return the matching {@code Happening}.
     * @throws IllegalArgumentException if not found.
     */
    @Override
    public Happening findById(long happeningId) {
        return happeningRepository.findById(happeningId)
                .orElseThrow(() -> new IllegalArgumentException("Happening not found"));
    }

    /**
     * Persists a new {@link Happening}.
     *
     * @param happening the instance to create.
     * @return the saved instance.
     */
    @Override
    public Happening create(Happening happening) {
        return happeningRepository.save(happening);
    }

    /**
     * Updates an existing {@link Happening} by applying changes from a new instance.
     * Ensures the types of the original and new happenings match, then delegates to the strategy.
     *
     * @param id ID of the happening to update.
     * @param newHappening the new values.
     * @return the updated and saved instance.
     * @throws IllegalArgumentException if types mismatch or ID not found.
     */
    @Override
    public Happening update(long id, Happening newHappening) {
        Happening existingHappening = findById(id);

        if (!existingHappening.getClass().equals(newHappening.getClass()))
            throw new IllegalArgumentException("Type mismatch");

        Happening updatedHappening = happeningStrategyResolver.update(existingHappening, newHappening);

        return happeningRepository.save(updatedHappening);
    }

    // is visibility range static?
    @Override
    public int setIncidentVisibilityRange(long incidentId, int range) {
        return 0;
    }

    /**
     * Extends the expiration time of an incident by confirming it.
     *
     * @param incidentId the ID of the incident.
     * @return a new {@link Incident} instance with extended lifespan.
     * @throws IllegalStateException if the given ID does not point to an {@code Incident}.
     */
    @Override
    public Incident extendIncidentLifespan(long incidentId) {
        Happening existingIncident = findById(incidentId);

        if (existingIncident instanceof Incident incident) {
            Incident extendedIncident = incident.confirmIncident();
            return (Incident) happeningRepository.save(extendedIncident);
        } else {
            throw new IllegalStateException("Expected Incident but got: " + existingIncident.getClass().getSimpleName());
        }
    }

    /**
     * Deletes a {@link Happening} by ID.
     *
     * @param incidentId the unique identifier of the entity to delete.
     */
    @Override
    public void delete(long incidentId) {
        happeningRepository.deleteById(incidentId);
    }
}
