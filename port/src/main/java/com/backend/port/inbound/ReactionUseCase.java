package com.backend.port.inbound;

import com.backend.port.inbound.commands.ReactionSummary;

/**
 * Defines use cases for managing reactions such as likes and dislikes.
 *
 * Each method returns a {@link ReactionSummary} reflecting the updated
 * state of reactions after the operation is applied.
 */
public interface ReactionUseCase {

    /**
     * Adds a like reaction.
     *
     * @return the updated reaction summary
     */
    ReactionSummary addLike();

    /**
     * Adds a dislike reaction.
     *
     * @return the updated reaction summary
     */
    ReactionSummary addDislike();

    /**
     * Removes a like reaction.
     *
     * @return the updated reaction summary
     */
    ReactionSummary removeLike();

    /**
     * Removes a dislike reaction.
     *
     * @return the updated reaction summary
     */
    ReactionSummary removeDislike();
}
