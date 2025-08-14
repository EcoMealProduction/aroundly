package com.backend.adapter.in.dto.response;

import lombok.Builder;

@Builder(toBuilder = true)
public record SentimentEngagementResponseDto(int likes, int dislikes) {

}
