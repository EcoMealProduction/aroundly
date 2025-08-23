package com.backend.port.out;

import com.backend.domain.happening.old.OldIncident;
import java.util.List;
import java.util.Optional;

/**
 * Repository interface for managing incidents in the data store.
 */
public interface IncidentRepository {

    /**
     * Saves a new incident or updates an existing one.
     *
     * @param oldIncident The incident to be saved or updated.
     * @return The saved or updated incident (with assigned ID if new).
     */
    OldIncident save(OldIncident oldIncident);

    /**
     * Finds a incident by its unique identifier.
     *
     * @param id The unique ID of the incident.
     * @return The incident if found.
     */
    Optional<OldIncident> findById(long id);

    /**
     * Retrieves all incidents in the data store.
     *
     * @return A list of all incidents.
     */
    List<OldIncident> findAll();

    /**
     * Finds all incidents within a specified range (e.g., geographical or time-based).
     * Consider adding parameters such as location coordinates and range value.
     *
     * @return A list of incidents within the given range.
     */
    List<OldIncident> findByAllInGivenRange(double lat, double lon, double radiusMeters);

    /**
     * Deletes an incident by its unique identifier.
     *
     * @param id The unique ID of the incident to delete.
     */
    void deleteById(long id);

    /**
     * Finds all incidents created by a specific user.
     *
     * @param userId The unique ID of the user.
     * @return A list of incidents created by the specified user.
     */
    List<OldIncident> findByUserId(long userId);
}
