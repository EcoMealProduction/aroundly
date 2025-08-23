package com.backend.port.outbound;

import com.backend.domain.reactions.SentimentEngagement;

/**
 * Repository interface for managing persistence of {@link SentimentEngagement} reactions.
 *
 * Provides methods for saving and deleting reaction states.
 */
public interface ReactionRepository {

    /**
     * Saves a sentiment engagement (likes/dislikes state).
     *
     * @param reaction the sentiment engagement to save
     * @return the persisted sentiment engagement
     */
    SentimentEngagement save(SentimentEngagement reaction);

    /**
     * Deletes a sentiment engagement.
     *
     * @param reaction the sentiment engagement to delete
     */
    void delete(SentimentEngagement reaction);
}
