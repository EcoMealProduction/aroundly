package com.backend.port.inbound;

import com.backend.domain.actor.Comment;
import com.backend.port.inbound.commands.CommentTextCommand;
import java.util.List;

/**
 * Defines use cases for managing comments, including creation, retrieval,
 * modification, deletion, and reactions.
 */
public interface CommentUseCase {

    /**
     * Creates a new comment.
     *
     * @param commentText the command object containing the comment text
     * @return the created comment
     */
    Comment create(CommentTextCommand commentText);

    /**
     * Finds all comments authored by a given actor.
     *
     * @param actorId the identifier of the actor
     * @return the list of comments by the actor
     */
    List<Comment> findByActorId(long actorId);

    /**
     * Finds all comments associated with a given Happening.
     *
     * @param happeningId the identifier of the Happening
     * @return the list of comments for the Happening
     */
    List<Comment> findByHappeningId(long happeningId);

    /**
     * Updates the text of an existing comment.
     *
     * @param commentId       the identifier of the comment to update
     * @param newCommentText  the command object containing the new text
     * @return the updated comment
     */
    Comment update(long commentId, CommentTextCommand newCommentText);

    /**
     * Deletes a comment by its identifier.
     *
     * @param commentId the identifier of the comment to delete
     */
    void delete(long commentId);
}
