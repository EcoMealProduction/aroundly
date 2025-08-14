package com.backend.adapter.in.mapper.response;

import com.backend.adapter.in.dto.response.SentimentEngagementResponseDto;
import com.backend.domain.shared.SentimentEngagement;
import org.mapstruct.Mapper;

/**
 * MapStruct mapper for converting the domain {@link SentimentEngagement} entity
 * into its corresponding {@link SentimentEngagementResponseDto} representation.
 *
 * This mapper is used to prepare sentiment reaction data for inclusion
 * in API responses.
 */
@Mapper(componentModel = "spring")
public interface SentimentEngagementResponseMapper {

  /**
   * Maps a domain {@link SentimentEngagement} to a {@link SentimentEngagementResponseDto}
   * for use in API responses.
   *
   * @param reaction the domain sentiment engagement
   * @return the response DTO
   */
  SentimentEngagementResponseDto toDto(SentimentEngagement reaction);
}
