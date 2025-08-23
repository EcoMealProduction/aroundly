package com.backend.in.mapper;

import com.backend.adapter.in.dto.media.MediaRefDto;
import com.backend.adapter.in.dto.metadata.EventMetadataDto;
import com.backend.adapter.in.dto.request.IncidentMetadataRequestDto;
import com.backend.adapter.in.dto.request.LocationRequestDto;
import com.backend.adapter.in.dto.response.CommentResponseDto;
import com.backend.adapter.in.dto.response.LocationResponseDto;
import com.backend.adapter.in.dto.response.SentimentEngagementResponseDto;
import com.backend.adapter.in.dto.response.incident.IncidentDetailedResponseDto;
import com.backend.adapter.in.dto.response.incident.IncidentMetadataResponseDto;
import com.backend.adapter.in.dto.response.incident.IncidentPreviewResponseDto;
import com.backend.adapter.in.dto.shared.IncidentEngagementStatsDto;
import com.backend.adapter.in.dto.metadata.IncidentMetadataDto;
import com.backend.adapter.in.dto.shared.LocationDto;
import com.backend.domain.happening.old.Event;
import com.backend.domain.happening.old.OldIncident;
import com.backend.domain.reactions.EngagementStats;
import com.backend.domain.media.MediaKind;
import com.backend.domain.media.Media;
import com.backend.domain.happening.metadata.EventMetadata;
import com.backend.domain.happening.metadata.IncidentMetadata;
import com.backend.adapter.in.dto.request.EventRequestDto;
import com.backend.adapter.in.dto.request.IncidentRequestDto;
import com.backend.adapter.in.dto.response.EventResponseDto;
import com.backend.adapter.in.dto.response.IncidentResponseOldDto;
import com.backend.domain.old.OldLocation;

import com.backend.domain.reactions.SentimentEngagement;
import com.backend.domain.actor.Actor;
import com.backend.domain.actor.ActorId;
import com.backend.domain.actor.Comment;
import com.backend.domain.actor.Role;
import java.math.BigDecimal;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public class MapperFixtures {

    private static final LocalDateTime SAFE_TIME = LocalDateTime.of(2026, 1, 1, 12, 0);
    public static final Actor vaneaUser = new Actor(new ActorId(1L), "vanea",  Set.of(Role.USER));
    public static final MediaRefDto mediaDto = new MediaRefDto(URI.create("/path/"));
    public static final Media mediaDomain = new Media(MediaKind.IMAGE, "content-type", URI.create("/path/"));
    public static final Comment commentDomain = Comment.builder()
        .actor(vaneaUser)
        .text("comment")
        .build();

    public static final SentimentEngagement PLAIN_OLD_SENTIMENT_ENGAGEMENT_DOMAIN = SentimentEngagement.builder()
        .build();

    public static final LocationDto locationDto = LocationDto.builder()
            .latitude(BigDecimal.valueOf(47.0101))
            .longitude(BigDecimal.valueOf(28.8576))
            .address("Strada Test, Chisinau")
            .build();

    public static final LocationRequestDto locationRequestDto = new LocationRequestDto(
        BigDecimal.valueOf(47.0101),
        BigDecimal.valueOf(28.8576),
        "Strada Test, Chisinau");

    public static final CommentResponseDto commentResponseDto = new CommentResponseDto("comment");
    public static final LocationResponseDto locationResponseDto = new LocationResponseDto("Strada Test, Chisinau");
    public static final IncidentMetadataResponseDto incidentMetadataResponseDto = IncidentMetadataResponseDto.builder()
        .username("vanea")
        .location(locationResponseDto)
        .media(Set.of(mediaDto))
        .build();
    public static final IncidentPreviewResponseDto incidentPreviewResponseDto = IncidentPreviewResponseDto.builder()
        .title("Test Incident Title")
        .metadata(incidentMetadataResponseDto)
        .build();

    public static final SentimentEngagementResponseDto sentimentEngagementResponseDto = SentimentEngagementResponseDto.builder()
        .build();

    public static final IncidentDetailedResponseDto incidentDetailedResponseDto = IncidentDetailedResponseDto.builder()
        .title("Test Incident Title")
        .description("Test Incident Description which is valid")
        .metadata(incidentMetadataResponseDto)
        .comments(List.of(commentResponseDto))
        .reaction(sentimentEngagementResponseDto)
        .build();

    public static final IncidentMetadataRequestDto incidentMetadataRequestDto = IncidentMetadataRequestDto.builder()
        .media(Set.of(mediaDto))
        .location(locationRequestDto)
        .build();

    public static final IncidentRequestDto incidentRequestDto = IncidentRequestDto.builder()
        .title("Test Incident Title")
        .description("Test Incident Description which is valid")
        .metadata(incidentMetadataRequestDto)
        .build();

    public static final OldLocation OLD_LOCATION = OldLocation.builder()
            .latitude(BigDecimal.valueOf(47.0101))
            .longitude(BigDecimal.valueOf(28.8576))
            .address("Strada Test, Chisinau")
            .build();

    public static final EventMetadataDto eventMetadataDto = EventMetadataDto.builder()
            .authorUsername("vanea")
            .location(locationDto)
            .startTime(SAFE_TIME.plusMinutes(40))
            .endTime(SAFE_TIME.plusMinutes(90))
            .build();

    public static final EventMetadata eventMetadata = EventMetadata.builder()
            .actor(vaneaUser)
            .oldLocation(OLD_LOCATION)
            .startTime(SAFE_TIME.plusMinutes(40))
            .endTime(SAFE_TIME.plusMinutes(90))
            .build();

    public static final IncidentMetadataDto incidentMetadataDto = IncidentMetadataDto.builder()
        .username("vanea")
        .location(locationDto)
        .media(Set.of(mediaDto))
        .createdAt(SAFE_TIME)
        .expirationTime(SAFE_TIME.plusMinutes(60))
        .build();

    public static final IncidentMetadata incidentMetadata = IncidentMetadata.builder()
        .actor(vaneaUser)
        .oldLocation(OLD_LOCATION)
        .media(Set.of(mediaDomain))
        .expirationTime(SAFE_TIME.plusMinutes(60))
        .build();

    public static final IncidentEngagementStatsDto statsDto = IncidentEngagementStatsDto.builder()
            .confirms(3)
            .denies(1)
            .consecutiveDenies(1)
            .build();

    public static final EngagementStats stats = EngagementStats.builder()
            .confirms(3)
            .denies(1)
            .consecutiveDenies(1)
            .build();

    public static final EventRequestDto eventRequestDto = EventRequestDto.builder()
            .title("Test Event Title")
            .description("Test Event Description")
            .eventMetadata(eventMetadataDto)
            .build();

//    public static final IncidentRequestDto incidentRequestDto = IncidentRequestDto.builder()
//            .title("Test Incident Title")
//            .description("Test Incident Description which is valid")
//            .metadata(incidentMetadataRequestDto)
//            .build();

    // FIX: Adaugă sentimentEngagement la event
    public static final Event domainEvent = new Event.Builder()
            .title(eventRequestDto.title())
            .description(eventRequestDto.description())
            .metadata(eventMetadata)
            .build();

    public static final OldIncident DOMAIN_OLD_INCIDENT = new OldIncident.Builder()
        .title("Valid Incident")
        .description("Valid long incident description")
        .metadata(incidentMetadata)
        .comments(List.of(commentDomain))
        .incidentEngagementStats(stats)
        .sentimentEngagement(new SentimentEngagement(0, 0))
        .build();

    public static final OldIncident DOMAIN_OLD_INCIDENT_FOR_CONTROLLER = new OldIncident.Builder()
        .title("Valid Incident")
        .description("Valid long incident description")
        .metadata(incidentMetadata)
        .build();

    public static final EventResponseDto eventResponseDto = EventResponseDto.builder()
            .title(domainEvent.title())
            .description(domainEvent.description())
            .eventMetadata(eventMetadataDto)
            .comments(List.of())
            .build();

    public static final IncidentResponseOldDto INCIDENT_RESPONSE_OLD_DTO = IncidentResponseOldDto.builder()
            .title(DOMAIN_OLD_INCIDENT.title())
            .description(DOMAIN_OLD_INCIDENT.description())
            .incidentMetadata(incidentMetadataDto)
            .comments(List.of())
            .engagementStats(statsDto)
            .build();

    private MapperFixtures() { }
}
