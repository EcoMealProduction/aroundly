package com.backend.port.outbound;

import com.backend.domain.actor.Comment;
import java.util.List;

/**
 * Repository interface for accessing and managing Comment entities in the data store.
 */
public interface CommentRepository {

    /**
     * Saves a new comment in the system.
     *
     * @param comment the comment to be saved
     * @return The saved Comment object, potentially with a generated ID.
     */
    Comment save(Comment comment);

    /**
     * Retrieves all comments associated with a specific happening.
     *
     * @param happeningId The ID of the happening whose comments are to be fetched.
     * @return A list of Comment objects related to the specified happening.
     */
    List<Comment> findByHappeningId(long happeningId);

    /**
     * Finds a comment by its unique identifier.
     *
     * @param id The unique ID of the comment to retrieve.
     * @return The Comment object if found, or null if not found.
     */
    Comment findById(long id);

    /**
     * Deletes a comment by its unique identifier.
     *
     * @param id The unique ID of the comment to delete.
     */
    void delete(long id);

    /**
     * Updates an existing comment identified by its ID.
     *
     * @param id The ID of the comment to edit.
     * @param updatedComment The Comment object containing updated fields.
     * @return The updated Comment object.
     */
    Comment update(long id, Comment updatedComment);
}
