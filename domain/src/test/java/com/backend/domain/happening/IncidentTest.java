package com.backend.domain.happening;

import com.backend.domain.happening.metadata.IncidentMetadata;
import com.backend.domain.user.Comment;
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
        assertEquals(vaneaUser, validIncident.metadata().actor());
        assertNotNull(validIncident.metadata().location());
        assertEquals(0, validIncident.sentimentEngagement().likes());
        assertEquals(0, validIncident.incidentEngagementStats().denies());
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
    void testIsDeletedReturnsTrueWhenConsecutiveDeniesIsAtLeast3() {
        IncidentEngagementStats updatedIncidentEngagementStats = validIncidentEngagementStats.toBuilder()
                .consecutiveDenies(3)
                .build();

        Incident incidentIsDeleted = validIncident.toBuilder()
                .incidentEngagementStats(updatedIncidentEngagementStats)
                .build();

        assertTrue(incidentIsDeleted.isDeleted());
    }

    @Test
    void testIsDeletedReturnsFalseWhenNotExpiredAndLowDenies() {
        IncidentEngagementStats updatedIncidentEngagementStats = validIncidentEngagementStats.toBuilder()
                .consecutiveDenies(1)
                .build();

        IncidentMetadata extendedExpirationTime = incidentMetadata.toBuilder()
                .expirationTime(LocalDateTime.now().plusMinutes(10))
                .build();

        Incident incident = validIncident.toBuilder()
                .incidentEngagementStats(updatedIncidentEngagementStats)
                .metadata(extendedExpirationTime)
                .build();

        assertFalse(incident.isDeleted());
    }

    @Test
    void testConfirmIncidentExtendsExpirationTimeAndResetsDenies() {
        IncidentEngagementStats updatedIncidentEngagementStats = validIncidentEngagementStats.toBuilder()
                .consecutiveDenies(2)
                .build();
        Incident incidentWithDenies = validIncident.toBuilder()
                .incidentEngagementStats(updatedIncidentEngagementStats)
                .build();

        LocalDateTime initial = incidentWithDenies.metadata().expirationTime();
        Incident incidentWithConfirm = incidentWithDenies.confirmIncident();

        assertEquals(1, incidentWithConfirm.incidentEngagementStats().confirms());
        assertEquals(0, incidentWithConfirm.incidentEngagementStats().consecutiveDenies());
        assertEquals(initial.plusMinutes(5), incidentWithConfirm.metadata().expirationTime());
    }

    @Test
    void testDenyIncidentAndIncrementConsecutiveDenies() {
        Incident incidentWithDeny = validIncident.denyIncident();
        assertEquals(1, incidentWithDeny.incidentEngagementStats().denies());
    }

    @Test
    void testIsDeletedTrueWhenConsecutiveDeniesIs3() {
        IncidentEngagementStats updatedIncidentEngagementStats = validIncidentEngagementStats.toBuilder()
                .consecutiveDenies(3)
                .build();

        Incident incidentIsDeleted = validIncident.toBuilder()
                .incidentEngagementStats(updatedIncidentEngagementStats)
                .build();

        assertTrue(incidentIsDeleted.isDeleted());
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
}
