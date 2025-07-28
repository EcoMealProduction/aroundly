package com.backend.in.dto.response;

import com.backend.in.dto.shared.CommentDto;
import com.backend.in.dto.shared.EventMetadataDto;
import com.backend.in.dto.shared.SentimentEngagementDto;
import lombok.Builder;

import java.util.List;

@Builder(toBuilder = true)
public record EventResponseDto(
        String title,
        String description,
        EventMetadataDto eventMetadata,
        SentimentEngagementDto sentiment,
        List<CommentDto> comments
) {}
