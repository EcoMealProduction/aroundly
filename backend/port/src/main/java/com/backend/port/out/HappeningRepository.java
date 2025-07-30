package com.backend.port.out;

import com.backend.happening.Happening;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for managing Happening entities in the data store.
 */
public interface HappeningRepository {

    /**
     * Saves a new happening or updates an existing one.
     *
     * @param happening The Happening object to be saved or updated.
     * @return The saved or updated Happening object (with assigned ID if new).
     */
    Happening save(Happening happening);

    /**
     * Finds a happening by its unique identifier.
     *
     * @param id The unique ID of the happening.
     * @return The Happening object if found.
     */
    Optional<Happening> findById(long id);

    /**
     * Retrieves all happenings in the data store.
     *
     * @return A list of all Happening objects.
     */
    List<Happening> findAll();

    /**
     * Finds all happenings within a specified range (e.g., geographical or time-based).
     * Consider adding parameters such as location coordinates and range value.
     *
     * @return A list of Happening objects within the given range.
     */
    List<Happening> findByAllInGivenRange(int range);

    /**
     * Deletes a happening by its unique identifier.
     *
     * @param id The unique ID of the happening to delete.
     */
    void deleteById(long id);

    /**
     * Finds all happenings created by a specific user.
     *
     * @param userId The unique ID of the user.
     * @return A list of Happening objects created by the specified user.
     */
    List<Happening> findByUserId(long userId);
}
