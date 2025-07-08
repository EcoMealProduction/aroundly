package com.backend.port.out;

import com.backend.user.Reaction;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for accessing and managing Reaction entities in the data store.
 */
public interface ReactionRepository {

    /**
     * Saves a new reaction or updates an existing reaction.
     *
     * @param reaction The Reaction object to be saved or updated.
     * @return The saved or updated Reaction object (with assigned ID if new).
     */
    Reaction save(Reaction reaction);

    /**
     * Retrieves all reactions associated with a specific comment.
     *
     * @param commentId The ID of the comment whose reactions are to be retrieved.
     * @return A list of Reaction objects related to the specified comment.
     */
    List<Reaction> findByCommentId(long commentId);

    /**
     * Retrieves all reactions associated with a specific happening.
     *
     * @param happeningId The ID of the happening whose reactions are to be retrieved.
     * @return A list of Reaction objects related to the specified happening.
     */
    List<Reaction> findByHappeningId(long happeningId);

    /**
     * Finds a reaction by its unique identifier.
     *
     * @param reactionId The unique ID of the reaction to retrieve.
     * @return An Optional containing the Reaction if found, or empty if not found.
     */
    Optional<Reaction> findById(long reactionId);

    /**
     * Deletes a reaction by its unique identifier.
     *
     * @param id The unique ID of the reaction to delete.
     */
    void deleteById(long id);
}
