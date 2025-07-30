package com.backend;

import com.backend.happening.Event;
import com.backend.happening.Incident;
import com.backend.happening.IncidentEngagementStats;
import com.backend.happening.metadata.EventMetadata;
import com.backend.happening.metadata.IncidentMetadata;
import com.backend.shared.Location;
import com.backend.shared.SentimentEngagement;
import com.backend.user.Comment;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public final class Fixtures {
    public static final String authorUsername = "testUser";
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
            .authorUsername(authorUsername)
            .location(validLocation)
            .startTime(LocalDateTime.now().plusHours(1))
            .endTime(LocalDateTime.now().plusHours(2))
            .build();

    public static final IncidentMetadata incidentMetadata = IncidentMetadata.builder()
            .authorUsername(authorUsername)
            .location(validLocation)
            .createdAt(LocalDateTime.now())
            .expirationTime(LocalDateTime.now().plusMinutes(45))
            .build();

    public static final Comment validComment = Comment.builder()
            .authorUsername(authorUsername)
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
