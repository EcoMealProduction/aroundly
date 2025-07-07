package com.backend.port.in;

import com.backend.user.Comment;
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
     * Retrieves comments of a happening.
     *
     * @return A list of comments of a happening.
     */
    List<Comment> findByHappeningId(long happeningId);

    /**
     * Edits an existing comment.
     *
     * @param id the id of existing comment
     * @return The updated comment.
     */
    Comment edit(long id, Comment newComment);

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
}
