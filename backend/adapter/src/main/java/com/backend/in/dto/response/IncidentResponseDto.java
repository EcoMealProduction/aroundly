package com.backend.in.dto.response;

import com.backend.in.dto.shared.*;
import lombok.Builder;

import java.util.List;

@Builder(toBuilder = true)
public record IncidentResponseDto(
        String title,
        String description,
        IncidentMetadataDto  incidentMetadata,
        SentimentEngagementDto sentiment,
        List<CommentDto> comments,
        IncidentEngagementStatsDto engagementStats
) {}
