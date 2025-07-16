package com.backend.domain;

import com.backend.happening.Incident;
import com.backend.user.Comment;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static com.backend.domain.Fixtures.*;
import static org.junit.jupiter.api.Assertions.*;

public class IncidentTest {

    @Test
    void testValidIncidentCreatedSuccessfully() {
        assertEquals("Accident rutier la intersecție", validIncident.title());
        assertEquals("Coliziune minoră între două autoturisme. Traficul este ușor îngreunat.",
                validIncident.description());
        assertEquals("testUser", validIncident.authorUsername());
        assertNotNull(validIncident.location());
        assertEquals(0, validIncident.likes());
        assertEquals(0, validIncident.denies());
    }

    @Test
    void testAddLike() {
        Incident incidentWithLike = validIncident.addLike();
        assertEquals(1, incidentWithLike.likes());
    }

    @Test
    void testRemoveLike() {
        Incident incidentWithLike = validIncident.addLike();
        Incident incidentWithoutLike = incidentWithLike.removeLike();
        assertEquals(0, incidentWithoutLike.likes());
    }

    @Test
    void testAddDislike() {
        Incident incidentWithDislike = validIncident.addDislike();
        assertEquals(1, incidentWithDislike.dislikes());
    }

    @Test
    void testRemoveDislike() {
        Incident incidentWithDislike = validIncident.addDislike();
        Incident incidentWithoutDislike = incidentWithDislike.removeDislike();
        assertEquals(0, incidentWithoutDislike.dislikes());
    }

    @Test
    void testAddComment() {
        Comment newComment = validComment.toBuilder()
                .text("Alt comentariu util.")
                .build();

        Incident incidentWithComment = validIncident.addComment(newComment);
        assertEquals(2, incidentWithComment.comments().size());
    }

    @Test
    void testIsExpired() {
        Incident validIncidentNotExpired = validIncident.toBuilder()
                .expirationTime(LocalDateTime.now().plusMinutes(10))
                .build();

        assertFalse(validIncidentNotExpired.isExpired());
    }

    @Test
    void testIsDeletedReturnsTrueWhenConsecutiveDeniesIsAtLeast3() {
        Incident incidentIsDeleted = validIncident.toBuilder()
                .consecutiveDenies(3)
                .build();

        assertTrue(incidentIsDeleted.isDeleted());
    }

    @Test
    void testIsDeletedReturnsFalseWhenNotExpiredAndLowDenies() {
        Incident incident = validIncident.toBuilder()
                .consecutiveDenies(1)
                .expirationTime(LocalDateTime.now().plusMinutes(10))
                .build();

        assertFalse(incident.isDeleted());
    }


    @Test
    void testAddConfirmExtendsExpirationTimeAndResetsDenies() {
        Incident incidentWithDenies = validIncident.toBuilder()
                .consecutiveDenies(2)
                .build();

        LocalDateTime initial = incidentWithDenies.expirationTime();

        Incident incidentWithConfirm = incidentWithDenies.addConfirm();

        assertEquals(1, incidentWithConfirm.confirms());
        assertEquals(0, incidentWithConfirm.consecutiveDenies());
        assertEquals(initial.plusMinutes(5), incidentWithConfirm.expirationTime());
    }

    @Test
    void testAddDenyAndIncrementConsecutiveDenies() {
        Incident incidentWithDeny = validIncident.addDeny();
        assertEquals(1, incidentWithDeny.denies());
    }

    @Test
    void testIsDeletedTrueWhenConsecutiveDeniesIs3() {
        Incident incidentIsDeleted = validIncident.toBuilder()
                .consecutiveDenies(3)
                .build();

        assertTrue(incidentIsDeleted.isDeleted());
    }

    @Test
    void testIsDeletedFalseWhenNotExpiredAndDeniesUnder3() {
        Incident incidentIsNotDeleted = validIncident.toBuilder()
                .consecutiveDenies(1)
                .expirationTime(LocalDateTime.now().plusMinutes(5))
                .build();

        assertFalse(incidentIsNotDeleted.isDeleted());
    }

    @Test
    void testNegativeValuesThrowException() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () ->
                validIncident.toBuilder()
                        .likes(-1)
                        .build()
        );
        assertEquals("Negative values not allowed.", ex.getMessage());
    }

    @Test
    void testShortTitleThrowsException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                validIncident.toBuilder()
                        .title("Scurt")
                        .build()
        );
        assertEquals("Title too short.", exception.getMessage());
    }

    @Test
    void testShortDescriptionThrowsException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                validIncident.toBuilder()
                        .description("Descriere scurtă")
                        .build()
        );
        assertEquals("Description too short.", exception.getMessage());
    }

    @Test
    void testExpirationTimeBeforeCreatedAtThrowsException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                validIncident.toBuilder()
                        .createdAt(LocalDateTime.now().plusMinutes(10))
                        .expirationTime(LocalDateTime.now().plusMinutes(5))
                        .build()
        );
        assertEquals("Expiration time cannot be before createdAt.", exception.getMessage());
    }

    @Test
    void testExpirationTimeBeforeNowThrowsException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                validIncident.toBuilder()
                        .createdAt(LocalDateTime.now().minusMinutes(30))
                        .expirationTime(LocalDateTime.now().minusMinutes(1))
                        .build()
        );
        assertEquals("Expiration time cannot be before now.", exception.getMessage());
    }
}
