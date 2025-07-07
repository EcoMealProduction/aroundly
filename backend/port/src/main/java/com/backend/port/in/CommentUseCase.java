package com.backend.port.in;

import com.backend.user.Comment;
import com.backend.user.Reaction;
import java.util.List;

/**
 * Defines use cases for managing comments, including creation, retrieval,
 * modification, deletion, and reactions.
 */
public interface CommentUseCase {
    /**
     * Creates a new comment.
     *
     * @param comment The comment to create (should not have an ID yet).
     * @return The created comment with assigned ID and other metadata.
     */
    Comment create(Comment comment);

    /**
     * Retrieves all comments.
     *
     * @return A list of all comments in the system.
     */
    List<Comment> findAll();

    /**
     * Edits an existing comment.
     *
     * @param id the id of existing comment
     * @return The updated comment.
     */
    Comment edit(long id);

    /**
     * Finds a comment by its unique identifier.
     *
     * @param id The ID of the comment to retrieve.
     * @return The found comment, or null if not found.
     */
    Comment findById(long id);

    /**
     * Deletes the specified comment.
     *
     * @param id the id of existing comment to delete.
     */
    void delete(long id);

    /**
     * Adds or updates a reaction (like, dislike, etc.) to a comment.
     *
     * @param reaction The reaction to apply to the comment.
     * @return The updated comment with the new or changed reaction.
     */
    Comment react(Reaction reaction);
}
