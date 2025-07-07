package com.backend.port.in;

import com.backend.happening.Happening;
import com.backend.happening.Incident;
import com.backend.user.Reaction;

/**
 * Defines use cases for managing "happenings" (events/incidents), including
 * creation, modification, reactions, visibility, extension, and deletion.
 */
public interface HappeningUseCase {
    /**
     * Creates a new happening (event or incident).
     *
     * @param happening The happening object to create (should not have an ID yet).
     * @return The created happening with assigned ID and metadata.
     */
    Happening create(Happening happening);

    /**
     * Edits an existing happening.
     *
     * @param id The id of existing happening to edit.
     * @return The updated happening.
     */
    Happening edit(long id);

    /**
     * Adds or updates a reaction to a happening.
     *
     * @param reaction The reaction to apply to the happening.
     * @return The updated happening reflecting the new or updated reaction.
     */
    Happening react(Reaction reaction);

    /**
     * Sets up the visibility range for a specific incident.
     * (E.g., how far the incident is visible geographically or by permission.)
     *
     * @param incident The incident for which to set the visibility range.
     * @return The configured visibility range (e.g., in meters or other unit).
     */
    int setUpIncidentVisibilityRange(Incident incident);

    /**
     * Extends an incident, possibly updating its duration or scope.
     *
     * @param incident The incident to extend.
     * @return The updated (extended) incident.
     */
    Incident extendIncident(long id);

    /**
     * Deletes the specified happening.
     *
     * @param id The id of existing happening to delete.
     */
    void delete(long id);
}
