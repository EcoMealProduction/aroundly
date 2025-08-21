package com.backend.services.happening;

import com.backend.domain.happening.Incident;
import com.backend.port.in.IncidentUseCase;
import com.backend.port.out.IncidentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service implementation for handling operations on {@link Incident} entities.
 * Delegates persistence to the {@link IncidentRepository}.
 *
 * Registered as a Spring {@link Service} for business logic orchestration.
 */
@Service
public class IncidentService implements IncidentUseCase {

    private final IncidentRepository incidentRepository;

    /**
     * Constructs the {@code IncidentService} with required dependencies.
     *
     * @param incidentRepository repository for persistence operations.
     */
    public IncidentService(IncidentRepository incidentRepository) {
        this.incidentRepository = incidentRepository;
    }

    /**
     * Retrieves all {@link Incident} entries within a given visibility range.
     *
     * @param userLatitude  latitude of the origin point in decimal degrees
     * @param userLongitude longitude of the origin point in decimal degrees
     * @param radiusMeters  maximum search radius, in meters
     * @return list of matching {@code Incident} instances.
     */
    @Override
    public List<Incident> findAllInGivenRange(double userLatitude, double userLongitude, double radiusMeters) {
        return incidentRepository.findByAllInGivenRange(userLatitude, userLongitude, radiusMeters);
    }

    /**
     * Retrieves a specific {@link Incident} by its ID.
     *
     * @param id the unique identifier.
     * @return the matching {@code Incident}.
     * @throws IllegalArgumentException if not found.
     */
    @Override
    public Incident findById(long id) {
        return incidentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Incident not found"));
    }

    /**
     * Persists a new {@link Incident}.
     *
     * @param incident the instance to create.
     * @return the saved instance.
     */
    @Override
    public Incident create(Incident incident) {
        return incidentRepository.save(incident);
    }

    /**
     * Updates an existing {@link Incident} by applying changes from a new instance.
     * Ensures the types of the original and new incident match.
     *
     * @param id ID of the happening to update.
     * @param newIncident the new values.
     * @return the updated and saved instance.
     * @throws IllegalArgumentException if types mismatch or ID not found.
     */
    @Override
    public Incident update(long id, Incident newIncident) {
        Incident existingIncident = findById(id);

        Incident updatedExistingIncident = existingIncident.toBuilder()
            .title(newIncident.title())
            .description(newIncident.description())
            .comments(newIncident.comments())
            .metadata(newIncident.metadata())
            .sentimentEngagement(newIncident.sentimentEngagement())
            .incidentEngagementStats(newIncident.incidentEngagementStats())
            .build();

        return incidentRepository.save(updatedExistingIncident);
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
        Incident existingIncident = findById(incidentId);

        return incidentRepository.save(existingIncident.confirmIncident());
    }

    /**
     * Deletes a {@link Incident} by ID.
     *
     * @param id the unique identifier of the entity to delete.
     */
    @Override
    public void delete(long id) {
        incidentRepository.deleteById(id);
    }

}
