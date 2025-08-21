package com.backend.domain.happening;

import com.backend.domain.happening.metadata.EventMetadata;
import com.backend.domain.user.Comment;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static com.backend.domain.Fixtures.*;
import static org.junit.jupiter.api.Assertions.*;

public class EventTest {

    @Test
    void testValidEventCreatedSuccessfully() {
        assertEquals("Concert în aer liber", validEvent.title());
        assertEquals("Eveniment cu muzică live și food trucks.", validEvent.description());
        assertEquals(vaneaUser, validEvent.metadata().actor());
        assertNotNull(validEvent.metadata().location());
        assertEquals(0, validEvent.sentimentEngagement().likes());
        assertEquals(0, validEvent.sentimentEngagement().dislikes());
        assertEquals(1, validEvent.comments().size());
    }

    @Test
    void testAddComment() {
        Comment newComment = validComment.toBuilder()
                .text("Alt comentariu util.")
                .build();

        Event eventWithComment = validEvent.addComment(newComment);
        assertEquals(2, eventWithComment.comments().size());
    }

    @Test
    void testIsFinished() {
        EventMetadata updatedEventMetadata = eventMetadata.toBuilder()
                .endTime(LocalDateTime.now().plusHours(4))
                .build();
        Event eventIsFinished = validEvent.toBuilder()
                .metadata(updatedEventMetadata)
                .build();

        assertFalse(eventIsFinished.metadata().isFinished());
    }

    @Test
    void testShortTitleThrowsException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                validEvent.toBuilder()
                        .title("Scurt")
                        .build()
        );

        assertEquals("Title too short.", exception.getMessage());
    }

    @Test
    void testShortDescriptionThrowsException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                validEvent.toBuilder()
                        .description("Scurt")
                        .build()
        );

        assertEquals("Description too short.", exception.getMessage());
    }
}
