package com.backend.domain;

import com.backend.domain.shared.SentimentEngagement;
import org.junit.jupiter.api.Test;

import static com.backend.domain.Fixtures.validSentimentEngagement;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SentimentEngagementTest {

    @Test
    void testAddLike() {
        SentimentEngagement addLike = validSentimentEngagement.addLike();
        assertEquals(1, addLike.likes());
    }

    @Test
    void testRemoveLike() {
        SentimentEngagement addLike = validSentimentEngagement.addLike();
        assertEquals(1, addLike.likes());

        SentimentEngagement removeLike = addLike.removeLike();
        assertEquals(0, removeLike.likes());
    }

    @Test
    void testAddDislike() {
        SentimentEngagement eventWithDislike = validSentimentEngagement.addDislike();
        assertEquals(1, eventWithDislike.dislikes());
    }

    @Test
    void testRemoveDislike() {
        SentimentEngagement addDislike = validSentimentEngagement.addDislike();
        assertEquals(1, addDislike.dislikes());

        SentimentEngagement removeDislike = addDislike.removeDislike();
        assertEquals(0, removeDislike.dislikes());
    }

    @Test
    void testNegativeLikesAndDislikesThrowsException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                validSentimentEngagement.toBuilder()
                        .likes(-1)
                        .dislikes(-1)
                        .build()
        );

        assertEquals("Likes and dislikes cannot be negative.", exception.getMessage());
    }
}
