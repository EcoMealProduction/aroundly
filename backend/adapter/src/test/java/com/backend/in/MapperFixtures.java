package com.backend.in;

import com.backend.adapter.in.dto.shared.EventMetadataDto;
import com.backend.adapter.in.dto.shared.IncidentEngagementStatsDto;
import com.backend.adapter.in.dto.shared.IncidentMetadataDto;
import com.backend.adapter.in.dto.shared.LocationDto;
import com.backend.domain.happening.Event;
import com.backend.domain.happening.Incident;
import com.backend.domain.happening.IncidentEngagementStats;
import com.backend.domain.happening.metadata.EventMetadata;
import com.backend.domain.happening.metadata.IncidentMetadata;
import com.backend.adapter.in.dto.request.EventRequestDto;
import com.backend.adapter.in.dto.request.IncidentRequestDto;
import com.backend.adapter.in.dto.response.EventResponseDto;
import com.backend.adapter.in.dto.response.IncidentResponseDto;
import com.backend.domain.shared.Location;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class MapperFixtures {

    private static final LocalDateTime SAFE_TIME = LocalDateTime.of(2026, 1, 1, 12, 0);

    public static final LocationDto locationDto = LocationDto.builder()
            .latitude(BigDecimal.valueOf(47.0101))
            .longitude(BigDecimal.valueOf(28.8576))
            .address("Strada Test, Chisinau")
            .build();

    public static final Location location = Location.builder()
            .latitude(BigDecimal.valueOf(47.0101))
            .longitude(BigDecimal.valueOf(28.8576))
            .address("Strada Test, Chisinau")
            .build();

    public static final EventMetadataDto eventMetadataDto = EventMetadataDto.builder()
            .authorUsername("tester")
            .location(locationDto)
            .startTime(SAFE_TIME.plusMinutes(40))
            .endTime(SAFE_TIME.plusMinutes(90))
            .build();

    public static final EventMetadata eventMetadata = EventMetadata.builder()
            .authorUsername("tester")
            .location(location)
            .startTime(SAFE_TIME.plusMinutes(40))
            .endTime(SAFE_TIME.plusMinutes(90))
            .build();

    public static final IncidentMetadataDto incidentMetadataDto = IncidentMetadataDto.builder()
            .authorUsername("incidentTester")
            .location(locationDto)
            .createdAt(SAFE_TIME)
            .expirationTime(SAFE_TIME.plusMinutes(60))
            .build();

    public static final IncidentMetadata incidentMetadata = IncidentMetadata.builder()
            .authorUsername("incidentTester")
            .location(location)
            .createdAt(SAFE_TIME)
            .expirationTime(SAFE_TIME.plusMinutes(60))
            .build();

    public static final IncidentEngagementStatsDto statsDto = IncidentEngagementStatsDto.builder()
            .confirms(3)
            .denies(1)
            .consecutiveDenies(1)
            .build();

    public static final IncidentEngagementStats stats = IncidentEngagementStats.builder()
            .confirms(3)
            .denies(1)
            .consecutiveDenies(1)
            .build();

    public static final EventRequestDto eventRequestDto = EventRequestDto.builder()
            .title("Test Event Title")
            .description("Test Event Description")
            .eventMetadata(eventMetadataDto)
            .build();

    public static final IncidentRequestDto incidentRequestDto = IncidentRequestDto.builder()
            .title("Test Incident Title")
            .description("Test Incident Description which is valid")
            .incidentMetadata(incidentMetadataDto)
            .build();

    // FIX: Adaugă sentimentEngagement la event
    public static final Event domainEvent = new Event.Builder()
            .title(eventRequestDto.title())
            .description(eventRequestDto.description())
            .metadata(eventMetadata)
            .build();

    // FIX: Adaugă sentimentEngagement la incident
    public static final Incident domainIncident = new Incident.Builder()
            .title(incidentRequestDto.title())
            .description(incidentRequestDto.description())
            .metadata(incidentMetadata)
            .incidentEngagementStats(stats)
            .build();

    public static final EventResponseDto eventResponseDto = EventResponseDto.builder()
            .title(domainEvent.title())
            .description(domainEvent.description())
            .eventMetadata(eventMetadataDto)
            .comments(List.of())
            .build();

    public static final IncidentResponseDto incidentResponseDto = IncidentResponseDto.builder()
            .title(domainIncident.title())
            .description(domainIncident.description())
            .incidentMetadata(incidentMetadataDto)
            .comments(List.of())
            .engagementStats(statsDto)
            .build();

    private MapperFixtures() { }
}
