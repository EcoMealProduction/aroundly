package com.backend.domain;

import com.backend.domain.reactions.SentimentEngagement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SentimentEngagementTest {

    private SentimentEngagement sentimentEngagement;

    @BeforeEach
    void setup() {
        sentimentEngagement = SentimentEngagement.builder()
            .build();
    }

    @Test
    void testAddLike() {
        SentimentEngagement addLike = sentimentEngagement.addLike();
        assertEquals(1, addLike.likes());
    }

    @Test
    void testRemoveLike() {
        SentimentEngagement addLike = sentimentEngagement.addLike();
        assertEquals(1, addLike.likes());

        SentimentEngagement removeLike = addLike.removeLike();
        assertEquals(0, removeLike.likes());
    }

    @Test
    void testAddDislike() {
        SentimentEngagement eventWithDislike = sentimentEngagement.addDislike();
        assertEquals(1, eventWithDislike.dislikes());
    }

    @Test
    void testRemoveDislike() {
        SentimentEngagement addDislike = sentimentEngagement.addDislike();
        assertEquals(1, addDislike.dislikes());

        SentimentEngagement removeDislike = addDislike.removeDislike();
        assertEquals(0, removeDislike.dislikes());
    }

    @Test
    void testNegativeLikesAndDislikesThrowsException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
            sentimentEngagement.toBuilder()
                        .likes(-1)
                        .dislikes(-1)
                        .build()
        );

        assertEquals("Likes and dislikes cannot be negative.", exception.getMessage());
    }
}
