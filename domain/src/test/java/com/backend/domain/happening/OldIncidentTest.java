package com.backend.domain.happening;

import com.backend.domain.happening.metadata.IncidentMetadata;
import com.backend.domain.actor.Comment;
import com.backend.domain.happening.old.OldIncident;
import com.backend.domain.reactions.EngagementStats;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static com.backend.domain.Fixtures.*;
import static org.junit.jupiter.api.Assertions.*;

public class OldIncidentTest {

    @Test
    void testValidIncidentCreatedSuccessfully() {
        assertEquals("Accident rutier la intersecție", VALID_OLD_INCIDENT.title());
        assertEquals("Coliziune minoră între două autoturisme. Traficul este ușor îngreunat.",
                VALID_OLD_INCIDENT.description());
        assertEquals(vaneaUser, VALID_OLD_INCIDENT.metadata().actor());
        assertNotNull(VALID_OLD_INCIDENT.metadata().oldLocation());
        assertEquals(0, VALID_OLD_INCIDENT.sentimentEngagement().likes());
        assertEquals(0, VALID_OLD_INCIDENT.engagementStats().denies());
    }

    @Test
    void testAddComment() {
        Comment newComment = validComment.toBuilder()
                .text("Alt comentariu util.")
                .build();

        OldIncident oldIncidentWithComment = VALID_OLD_INCIDENT.addComment(newComment);
        assertEquals(2, oldIncidentWithComment.comments().size());
    }

    @Test
    void testIsDeletedReturnsTrueWhenConsecutiveDeniesIsAtLeast3() {
        EngagementStats updatedEngagementStats = VALID_ENGAGEMENT_STATS.toBuilder()
                .consecutiveDenies(3)
                .build();

        OldIncident oldIncidentIsDeleted = VALID_OLD_INCIDENT.toBuilder()
                .incidentEngagementStats(updatedEngagementStats)
                .build();

        assertTrue(oldIncidentIsDeleted.isDeleted());
    }

    @Test
    void testIsDeletedReturnsFalseWhenNotExpiredAndLowDenies() {
        EngagementStats updatedEngagementStats = VALID_ENGAGEMENT_STATS.toBuilder()
                .consecutiveDenies(1)
                .build();

        IncidentMetadata extendedExpirationTime = incidentMetadata.toBuilder()
                .expirationTime(LocalDateTime.now().plusMinutes(10))
                .build();

        OldIncident oldIncident = VALID_OLD_INCIDENT.toBuilder()
                .incidentEngagementStats(updatedEngagementStats)
                .metadata(extendedExpirationTime)
                .build();

        assertFalse(oldIncident.isDeleted());
    }

    @Test
    void testConfirmIncidentExtendsExpirationTimeAndResetsDenies() {
        EngagementStats updatedEngagementStats = VALID_ENGAGEMENT_STATS.toBuilder()
                .consecutiveDenies(2)
                .build();
        OldIncident oldIncidentWithDenies = VALID_OLD_INCIDENT.toBuilder()
                .incidentEngagementStats(updatedEngagementStats)
                .build();

        LocalDateTime initial = oldIncidentWithDenies.metadata().expirationTime();
        OldIncident oldIncidentWithConfirm = oldIncidentWithDenies.confirmIncident();

        assertEquals(1, oldIncidentWithConfirm.engagementStats().confirms());
        assertEquals(0, oldIncidentWithConfirm.engagementStats().consecutiveDenies());
        assertEquals(initial.plusMinutes(5), oldIncidentWithConfirm.metadata().expirationTime());
    }

    @Test
    void testDenyIncidentAndIncrementConsecutiveDenies() {
        OldIncident oldIncidentWithDeny = VALID_OLD_INCIDENT.denyIncident();
        assertEquals(1, oldIncidentWithDeny.engagementStats().denies());
    }

    @Test
    void testIsDeletedTrueWhenConsecutiveDeniesIs3() {
        EngagementStats updatedEngagementStats = VALID_ENGAGEMENT_STATS.toBuilder()
                .consecutiveDenies(3)
                .build();

        OldIncident oldIncidentIsDeleted = VALID_OLD_INCIDENT.toBuilder()
                .incidentEngagementStats(updatedEngagementStats)
                .build();

        assertTrue(oldIncidentIsDeleted.isDeleted());
    }

    @Test
    void testShortTitleThrowsException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                VALID_OLD_INCIDENT.toBuilder()
                        .title("Scurt")
                        .build()
        );
        assertEquals("Title too short.", exception.getMessage());
    }

    @Test
    void testShortDescriptionThrowsException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                VALID_OLD_INCIDENT.toBuilder()
                        .description("Descriere scurtă")
                        .build()
        );
        assertEquals("Description too short.", exception.getMessage());
    }
}
