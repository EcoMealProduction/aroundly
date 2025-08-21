package com.backend.adapter.in.dto.response.incident;

import com.backend.adapter.in.dto.response.CommentResponseDto;
import com.backend.adapter.in.dto.response.SentimentEngagementResponseDto;
import java.util.List;
import lombok.Builder;

@Builder(toBuilder = true)
public record IncidentDetailedResponseDto(
    String title,
    String description,
    IncidentMetadataResponseDto metadata,
    List<CommentResponseDto> comments,
    SentimentEngagementResponseDto reaction) { }
