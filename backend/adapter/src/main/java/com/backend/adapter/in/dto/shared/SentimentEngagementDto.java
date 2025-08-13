package com.backend.adapter.in.dto.shared;

import lombok.Builder;

/**
 * Data Transfer Object for tracking user sentiment engagement.
 *
 * Records how users react to content through simple like/dislike
 * mechanisms to gauge community sentiment.
 *
 * @param likes    Number of positive reactions
 * @param dislikes Number of negative reactions
 */
@Builder(toBuilder = true)
public record SentimentEngagementDto(
        int likes,
        int dislikes) {}
