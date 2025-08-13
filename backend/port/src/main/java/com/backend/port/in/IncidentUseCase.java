package com.backend.port.in;

import com.backend.domain.happening.Incident;

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
    List<Incident> findAllInGivenRange(int range);

    /**
     * Finds a incident by its unique identifier.
     *
     * @param id The id of the incident to retrieve.
     * @return The found incident, or null if not found.
     */
    Incident findById(long id);

    /**
     * Creates a new incident.
     *
     * @param incident The incident object to create (should not have an ID yet).
     * @return The created incident with assigned ID and metadata.
     */
    Incident create(Incident incident);

    /**
     * Updates an existing incident.
     *
     * @param id The id of an existing incident to edit.
     * @param newIncident updated incident
     * @return The updated incident.
     */
    Incident update(long id, Incident newIncident);

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
    Incident extendIncidentLifespan(long id);

    /**
     * Deletes the specified incident.
     *
     * @param id The id of incident to delete.
     */
    void delete(long id);
}
