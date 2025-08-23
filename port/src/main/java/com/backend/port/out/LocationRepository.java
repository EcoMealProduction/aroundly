package com.backend.port.out;

import com.backend.domain.old.OldLocation;

import java.util.Optional;

/**
 * Repository interface for accessing and managing Location entities in the data store.
 */
public interface LocationRepository {

    /**
     * Saves a new location or updates an existing location.
     *
     * @param oldLocation The Location object to be saved or updated.
     * @return The saved or updated Location object (with assigned ID if new).
     */
    OldLocation save(OldLocation oldLocation);

    /**
     * Finds a location by its unique identifier.
     *
     * @param id The unique ID of the location to retrieve.
     * @return An Optional containing the Location if found, or empty if not found.
     */
    Optional<OldLocation> findById(long id);

    /**
     * Finds a location by its latitude and longitude coordinates.
     *
     * @param latitude  The latitude coordinate of the location.
     * @param longitude The longitude coordinate of the location.
     * @return An Optional containing the Location if found, or empty if not found.
     */
    Optional<OldLocation> findByCoordinate(double latitude, double longitude);

    /**
     * Deletes a location by its unique identifier.
     *
     * @param id The unique ID of the location to delete.
     */
    void deleteById(long id);
}
