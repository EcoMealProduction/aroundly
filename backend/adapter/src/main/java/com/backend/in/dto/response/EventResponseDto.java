package com.backend.in.dto.response;

import com.backend.in.dto.shared.CommentDto;
import com.backend.in.dto.shared.EventMetadataDto;
import com.backend.in.dto.shared.SentimentEngagementDto;
import lombok.Builder;

import java.util.List;

/**
 * Data Transfer Object for returning complete event information.
 * <p>
 * Provides comprehensive event details including user interactions
 * and community feedback for display purposes.
 * </p>
 *
 * @param title          The name or title of the event
 * @param description    Detailed description of the event
 * @param eventMetadata  Location and timing information
 * @param sentiment      Community sentiment and engagement metrics
 * @param comments       User comments and discussions about the event
 */
@Builder(toBuilder = true)
public record EventResponseDto(
        String title,
        String description,
        EventMetadataDto eventMetadata,
        SentimentEngagementDto sentiment,
        List<CommentDto> comments) {}
