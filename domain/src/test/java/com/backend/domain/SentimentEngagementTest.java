package com.backend.domain;

import com.backend.domain.reactions.SentimentEngagement;
import org.junit.jupiter.api.Test;

import static com.backend.domain.Fixtures.VALID_OLD_SENTIMENT_ENGAGEMENT;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SentimentEngagementTest {

    @Test
    void testAddLike() {
        SentimentEngagement addLike = VALID_OLD_SENTIMENT_ENGAGEMENT.addLike();
        assertEquals(1, addLike.likes());
    }

    @Test
    void testRemoveLike() {
        SentimentEngagement addLike = VALID_OLD_SENTIMENT_ENGAGEMENT.addLike();
        assertEquals(1, addLike.likes());

        SentimentEngagement removeLike = addLike.removeLike();
        assertEquals(0, removeLike.likes());
    }

    @Test
    void testAddDislike() {
        SentimentEngagement eventWithDislike = VALID_OLD_SENTIMENT_ENGAGEMENT.addDislike();
        assertEquals(1, eventWithDislike.dislikes());
    }

    @Test
    void testRemoveDislike() {
        SentimentEngagement addDislike = VALID_OLD_SENTIMENT_ENGAGEMENT.addDislike();
        assertEquals(1, addDislike.dislikes());

        SentimentEngagement removeDislike = addDislike.removeDislike();
        assertEquals(0, removeDislike.dislikes());
    }

    @Test
    void testNegativeLikesAndDislikesThrowsException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                VALID_OLD_SENTIMENT_ENGAGEMENT.toBuilder()
                        .likes(-1)
                        .dislikes(-1)
                        .build()
        );

        assertEquals("Likes and dislikes cannot be negative.", exception.getMessage());
    }
}
