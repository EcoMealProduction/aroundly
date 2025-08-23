package com.backend;

import com.backend.domain.happening.old.Event;
import com.backend.domain.happening.old.OldIncident;
import com.backend.domain.reactions.EngagementStats;
import com.backend.domain.happening.metadata.EventMetadata;
import com.backend.domain.happening.metadata.IncidentMetadata;
import com.backend.domain.old.OldLocation;
import com.backend.domain.reactions.SentimentEngagement;
import com.backend.domain.actor.Actor;
import com.backend.domain.actor.ActorId;
import com.backend.domain.actor.Comment;

import com.backend.domain.actor.Role;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public final class Fixtures {
    public static final Actor vaneaUser = new Actor(
        new ActorId(1L),
        "vanea",
        Set.of(Role.USER));

    public static final OldLocation VALID_OLD_LOCATION = OldLocation.builder()
            .latitude(BigDecimal.valueOf(47.0))
            .longitude(BigDecimal.valueOf(28.0))
            .address("Strada Stefan cel Mare 1, Chisinau")
            .build();

    public static final SentimentEngagement VALID_OLD_SENTIMENT_ENGAGEMENT = SentimentEngagement.builder()
            .likes(0)
            .dislikes(0)
            .build();

    public static final EngagementStats VALID_ENGAGEMENT_STATS = EngagementStats.builder()
            .confirms(0)
            .denies(0)
            .consecutiveDenies(0)
            .build();

    public static final EventMetadata eventMetadata = EventMetadata.builder()
        .actor(vaneaUser)
        .oldLocation(VALID_OLD_LOCATION)
        .startTime(LocalDateTime.now().plusHours(1))
        .endTime(LocalDateTime.now().plusHours(2))
        .build();

    public static final IncidentMetadata incidentMetadata = IncidentMetadata.builder()
        .actor(vaneaUser)
        .oldLocation(VALID_OLD_LOCATION)
        .createdAt(LocalDateTime.now())
        .expirationTime(LocalDateTime.now().plusMinutes(45))
        .build();

    public static final Comment validComment = Comment.builder()
        .actor(vaneaUser)
        .text("Un comentariu valid pentru test.")
        .createdAt(LocalDateTime.now())
        .build();

    public static final Event validEvent = new Event.Builder()
        .title("Concert în aer liber")
        .description("Eveniment cu muzică live și food trucks.")
        .comments(List.of(validComment))
        .sentimentEngagement(VALID_OLD_SENTIMENT_ENGAGEMENT)
        .metadata(eventMetadata)
        .build();

    public static final OldIncident VALID_OLD_INCIDENT = new OldIncident.Builder()
        .title("Accident rutier la intersecție")
        .description("Coliziune minoră între două autoturisme. Traficul este ușor îngreunat.")
        .comments(List.of(validComment))
        .metadata(incidentMetadata)
        .sentimentEngagement(VALID_OLD_SENTIMENT_ENGAGEMENT)
        .incidentEngagementStats(VALID_ENGAGEMENT_STATS)
        .build();

    private Fixtures() { }
}
