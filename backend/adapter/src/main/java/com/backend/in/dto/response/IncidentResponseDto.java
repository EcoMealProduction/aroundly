package com.backend.in.dto.response;

import com.backend.in.dto.shared.*;
import lombok.Builder;

import java.util.List;

/**
 * Data Transfer Object for returning complete incident information.
 * <p>
 * Provides comprehensive incident details including user interactions,
 * community feedback, and engagement statistics.
 * </p>
 *
 * @param title            Brief summary or title of the incident
 * @param description      Detailed account of what happened
 * @param incidentMetadata Location and timing information
 * @param sentiment        Community sentiment and engagement metrics
 * @param comments         User comments and discussions about the incident
 * @param engagementStats  Statistical data about user interactions
 */
@Builder(toBuilder = true)
public record IncidentResponseDto(
        String title,
        String description,
        IncidentMetadataDto  incidentMetadata,
        SentimentEngagementDto sentiment,
        List<CommentDto> comments,
        IncidentEngagementStatsDto engagementStats) {}
