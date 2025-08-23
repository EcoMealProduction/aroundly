package com.backend.port.in;

import com.backend.domain.happening.old.OldIncident;

import java.util.List;

/**
 * Defines use cases for managing incidents including
 * creation, modification, reactions, visibility, extension, and deletion.
 */
public interface IncidentUseCase {

    /**
     * Retrieves all incidents in a given range.
     *
     * @return A list of all incidents in the given range.
     */
    List<OldIncident> findAllInGivenRange(double lat, double lon, double radiusMeter);

    /**
     * Finds a incident by its unique identifier.
     *
     * @param id The id of the incident to retrieve.
     * @return The found incident, or null if not found.
     */
    OldIncident findById(long id);

    /**
     * Creates a new incident.
     *
     * @param oldIncident The incident object to create (should not have an ID yet).
     * @return The created incident with assigned ID and metadata.
     */
    OldIncident create(OldIncident oldIncident);

    /**
     * Updates an existing incident.
     *
     * @param id The id of an existing incident to edit.
     * @param newOldIncident updated incident
     * @return The updated incident.
     */
    OldIncident update(long id, OldIncident newOldIncident);

    /**
     * Sets up the visibility range for a specific incident.
     * (E.g., how far the incident is visible geographically or by permission.)
     *
     * @param id The id of incident for which to set the visibility range.
     * @param range the visibility range of incident
     * @return The configured visibility range (e.g., in meters or another unit).
     */
    int setIncidentVisibilityRange(long id, int range);

    /**
     * Extends an incident, possibly updating its duration or scope.
     *
     * @param id The id of incident to extend.
     * @return The updated (extended) incident.
     */
    OldIncident extendIncidentLifespan(long id);

    /**
     * Deletes the specified incident.
     *
     * @param id The id of incident to delete.
     */
    void delete(long id);
}
