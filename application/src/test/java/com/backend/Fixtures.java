package com.backend;

import com.backend.domain.happening.Event;
import com.backend.domain.happening.Incident;
import com.backend.domain.happening.IncidentEngagementStats;
import com.backend.domain.happening.metadata.EventMetadata;
import com.backend.domain.happening.metadata.IncidentMetadata;
import com.backend.domain.shared.Location;
import com.backend.domain.shared.SentimentEngagement;
import com.backend.domain.user.Actor;
import com.backend.domain.user.ActorId;
import com.backend.domain.user.Comment;

import com.backend.domain.user.Role;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public final class Fixtures {
    public static final Actor vaneaUser = new Actor(
        new ActorId(1L),
        "vanea",
        Set.of(Role.USER));

    public static final Location validLocation = Location.builder()
            .latitude(BigDecimal.valueOf(47.0))
            .longitude(BigDecimal.valueOf(28.0))
            .address("Strada Stefan cel Mare 1, Chisinau")
            .build();

    public static final SentimentEngagement validSentimentEngagement = SentimentEngagement.builder()
            .likes(0)
            .dislikes(0)
            .build();

    public static final IncidentEngagementStats validIncidentEngagementStats = IncidentEngagementStats.builder()
            .confirms(0)
            .denies(0)
            .consecutiveDenies(0)
            .build();

    public static final EventMetadata eventMetadata = EventMetadata.builder()
        .actor(vaneaUser)
        .location(validLocation)
        .startTime(LocalDateTime.now().plusHours(1))
        .endTime(LocalDateTime.now().plusHours(2))
        .build();

    public static final IncidentMetadata incidentMetadata = IncidentMetadata.builder()
        .actor(vaneaUser)
        .location(validLocation)
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
        .sentimentEngagement(validSentimentEngagement)
        .metadata(eventMetadata)
        .build();

    public static final Incident validIncident = new Incident.Builder()
        .title("Accident rutier la intersecție")
        .description("Coliziune minoră între două autoturisme. Traficul este ușor îngreunat.")
        .comments(List.of(validComment))
        .metadata(incidentMetadata)
        .sentimentEngagement(validSentimentEngagement)
        .incidentEngagementStats(validIncidentEngagementStats)
        .build();

    private Fixtures() { }
}
