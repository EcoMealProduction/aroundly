package com.backend.port.in;

import com.backend.user.Reaction;
import java.util.List;

/**
 * Defines use cases for handling reactions to comments and happenings,
 * as well as retrieving reactions by their associated entities.
 */
public interface ReactionUseCase {

    /**
     * Adds or updates a reaction on a specific comment.
     *
     * @param commentId The ID of the comment to react to.
     * @param reaction  The Reaction object containing reaction details (e.g., type, user).
     * @return The created or updated Reaction object.
     */
    Reaction reactToComment(long commentId, Reaction reaction);

    /**
     * Adds or updates a reaction on a specific happening.
     *
     * @param happeningId The ID of the happening to react to.
     * @param reaction    The Reaction object containing reaction details (e.g., type, user).
     * @return The created or updated Reaction object.
     */
    Reaction reactToHappening(long happeningId, Reaction reaction);

    /**
     * Retrieves all reactions associated with a specific comment.
     *
     * @param commentId The ID of the comment whose reactions are to be fetched.
     * @return A list of Reaction objects associated with the given comment.
     */
    List<Reaction> findByCommentId(long commentId);

    /**
     * Retrieves all reactions associated with a specific happening.
     *
     * @param happeningId The ID of the happening whose reactions are to be fetched.
     * @return A list of Reaction objects associated with the given happening.
     */
    List<Reaction> findByHappeningId(long happeningId);
}
