package com.backend.domain;

import com.backend.happening.Event;
import com.backend.happening.Incident;
import com.backend.shared.Location;
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

    public static final Comment validComment = Comment.builder()
            .authorUsername(authorUsername)
            .text("Un comentariu valid pentru test.")
            .createdAt(LocalDateTime.now())
            .build();

    public static final Event validEvent = Event.builder()
            .title("Concert în aer liber")
            .description("Eveniment cu muzică live și food trucks.")
            .authorUsername(authorUsername)
            .location(validLocation)
            .comments(List.of(validComment))
            .likes(0)
            .dislikes(0)
            .startTime(LocalDateTime.now().plusHours(1))
            .endTime(LocalDateTime.now().plusHours(2))
            .build();

    public static final Incident validIncident = Incident.builder()
            .title("Accident rutier la intersecție")
            .description("Coliziune minoră între două autoturisme. Traficul este ușor îngreunat.")
            .authorUsername(authorUsername)
            .location(validLocation)
            .comments(List.of(validComment))
            .likes(0)
            .dislikes(0)
            .confirms(0)
            .denies(0)
            .consecutiveDenies(0)
            .createdAt(LocalDateTime.now())
            .expirationTime(LocalDateTime.now().plusMinutes(45))
            .build();

    private Fixtures() { }
}
