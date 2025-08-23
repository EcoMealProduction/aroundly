package com.backend.services.happening;

import com.backend.domain.happening.old.OldIncident;
import com.backend.port.in.IncidentUseCase;
import com.backend.port.out.IncidentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service implementation for handling operations on {@link OldIncident} entities.
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
     * Retrieves all {@link OldIncident} entries within a given visibility range.
     *
     * @param userLatitude  latitude of the origin point in decimal degrees
     * @param userLongitude longitude of the origin point in decimal degrees
     * @param radiusMeters  maximum search radius, in meters
     * @return list of matching {@code Incident} instances.
     */
    @Override
    public List<OldIncident> findAllInGivenRange(double userLatitude, double userLongitude, double radiusMeters) {
        return incidentRepository.findByAllInGivenRange(userLatitude, userLongitude, radiusMeters);
    }

    /**
     * Retrieves a specific {@link OldIncident} by its ID.
     *
     * @param id the unique identifier.
     * @return the matching {@code Incident}.
     * @throws IllegalArgumentException if not found.
     */
    @Override
    public OldIncident findById(long id) {
        return incidentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Incident not found"));
    }

    /**
     * Persists a new {@link OldIncident}.
     *
     * @param oldIncident the instance to create.
     * @return the saved instance.
     */
    @Override
    public OldIncident create(OldIncident oldIncident) {
        return incidentRepository.save(oldIncident);
    }

    /**
     * Updates an existing {@link OldIncident} by applying changes from a new instance.
     * Ensures the types of the original and new incident match.
     *
     * @param id ID of the happening to update.
     * @param newOldIncident the new values.
     * @return the updated and saved instance.
     * @throws IllegalArgumentException if types mismatch or ID not found.
     */
    @Override
    public OldIncident update(long id, OldIncident newOldIncident) {
        OldIncident existingOldIncident = findById(id);

        OldIncident updatedExistingOldIncident = existingOldIncident.toBuilder()
            .title(newOldIncident.title())
            .description(newOldIncident.description())
            .comments(newOldIncident.comments())
            .metadata(newOldIncident.metadata())
            .sentimentEngagement(newOldIncident.sentimentEngagement())
            .incidentEngagementStats(newOldIncident.engagementStats())
            .build();

        return incidentRepository.save(updatedExistingOldIncident);
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
     * @return a new {@link OldIncident} instance with extended lifespan.
     * @throws IllegalStateException if the given ID does not point to an {@code Incident}.
     */
    @Override
    public OldIncident extendIncidentLifespan(long incidentId) {
        OldIncident existingOldIncident = findById(incidentId);

        return incidentRepository.save(existingOldIncident.confirmIncident());
    }

    /**
     * Deletes a {@link OldIncident} by ID.
     *
     * @param id the unique identifier of the entity to delete.
     */
    @Override
    public void delete(long id) {
        incidentRepository.deleteById(id);
    }

}
