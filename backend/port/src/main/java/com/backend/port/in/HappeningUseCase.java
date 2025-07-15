package com.backend.port.in;

import com.backend.happening.Happening;
import com.backend.happening.Incident;

import java.util.List;
import java.util.Optional;

/**
 * Defines use cases for managing "happenings" (events/incidents), including
 * creation, modification, reactions, visibility, extension, and deletion.
 */
public interface HappeningUseCase {
    /**
     * Retrieves all happenings.
     *
     * @return A list of all happenings in the system.
     */
    List<Happening> findAll();

    /**
     * Retrieves all happenings in given range.
     *
     * @return A list of all happenings in given range.
     */
    List<Happening> findAllInGivenRange(int range);

    /**
     * Finds a happening by its unique identifier.
     *
     * @param happeningId The id of the happening to retrieve.
     * @return The found happening, or null if not found.
     */
    Optional<Happening> findById(long happeningId);

    /**
     * Creates a new happening (event or incident).
     *
     * @param happening The happening object to create (should not have an ID yet).
     * @return The created happening with assigned ID and metadata.
     */
    Happening create(Happening happening);

    /**
     * Updates an existing happening.
     *
     * @param id The id of existing happening to edit.
     * @param newHappening updated happening
     * @return The updated happening.
     */
    Happening updated(long id, Happening newHappening);

    /**
     * Sets up the visibility range for a specific incident.
     * (E.g., how far the incident is visible geographically or by permission.)
     *
     * @param incidentId The id of incident for which to set the visibility range.
     * @param range the visibility range of incident
     * @return The configured visibility range (e.g., in meters or other unit).
     */
    int setIncidentVisibilityRange(long incidentId, int range);

    /**
     * Extends an incident, possibly updating its duration or scope.
     *
     * @param incidentId The id of incident to extend.
     * @return The updated (extended) incident.
     */
    Incident extendIncidentLifespan(long incidentId);

    /**
     * Deletes the specified happening.
     *
     * @param incidentId The id of happening to delete.
     */
    void delete(long incidentId);
}
