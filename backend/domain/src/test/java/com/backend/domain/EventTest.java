package com.backend.domain;

import com.backend.happening.Event;
import com.backend.user.Comment;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static com.backend.domain.Fixtures.*;
import static org.junit.jupiter.api.Assertions.*;

public class EventTest {

    @Test
    void testValidEventCreatedSuccessfully() {
        Event event = validEvent;

        assertEquals("Concert în aer liber", event.title());
        assertEquals("Eveniment cu muzică live și food trucks.", event.description());
        assertEquals("testUser", event.authorUsername());
        assertNotNull(event.location());
        assertEquals(0, event.likes());
        assertEquals(0, event.dislikes());
        assertEquals(1, event.comments().size());
    }

    @Test
    void testAddLike() {
        Event eventWithLike =  validEvent.addLike();
        assertEquals(1, eventWithLike.likes());
    }

    @Test
    void testRemoveLike() {
        Event eventWithLike =  validEvent.addLike();
        Event eventWithoutLike =  eventWithLike.removeLike();
        assertEquals(0, eventWithoutLike.likes());
    }

    @Test
    void testAddDislike() {
        Event eventWithDislike =  validEvent.addDislike();
        assertEquals(1, eventWithDislike.dislikes());
    }

    @Test
    void testRemoveDislike() {
        Event eventWithDislike =  validEvent.addDislike();
        Event eventWithoutDislike =  eventWithDislike.removeDislike();
        assertEquals(0, eventWithoutDislike.dislikes());
    }

    @Test
    void testAddComment() {
        Comment newComment = validComment.toBuilder()
                .text("Alt comentariu util.")
                .build();

        Event eventWithComment =  validEvent.addComment(newComment);
        assertEquals(2, eventWithComment.comments().size());
    }

    @Test
    void testShortTitleThrowsException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                validEvent.toBuilder().title("Scurt").build()
        );

        assertEquals("Title too short.", exception.getMessage());
    }

    @Test
    void testShortDescriptionThrowsException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                validEvent.toBuilder().description("Scurt").build()
        );

        assertEquals("Description too short.", exception.getMessage());
    }

    @Test
    void testNegativeLikesAndDislikesThrowsException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                validEvent.toBuilder().likes(-1).dislikes(-1).build()
        );

        assertEquals("Likes and dislikes cannot be negative.", exception.getMessage());
    }

    @Test
    void testStartTimeBeforeNowThrowsException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                validEvent.toBuilder().startTime(LocalDateTime.now().minusMinutes(5)).build()
        );

        assertEquals("Start time cannot be before now.", exception.getMessage());
    }

    @Test
    void testEndTimeBeforeNowThrowsException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                validEvent.toBuilder().endTime(LocalDateTime.now().minusMinutes(5)).build()
        );

        assertEquals("End time cannot be before now.", exception.getMessage());
    }

    @Test
    void testTooShortDurationThrowsException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                validEvent.toBuilder()
                        .startTime(LocalDateTime.now().plusMinutes(5))
                        .endTime(LocalDateTime.now().plusMinutes(15))
                        .build()
        );

        assertEquals("Event duration must be at least 30 minutes.", exception.getMessage());
    }



}
