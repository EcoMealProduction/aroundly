package com.backend.adapter.in.dto.shared;

import lombok.Builder;
import lombok.NonNull;

import java.time.LocalDateTime;

/**
 * Data Transfer Object representing a user comment.
 * <p>
 * Contains all information about a single comment including
 * the author, content, and when it was posted.
 * </p>
 *
 * @param authorUsername Name of the user who wrote the comment
 * @param text           The actual comment content
 * @param createdAt      When the comment was posted
 */
@Builder(toBuilder = true)
public record CommentDto(
        @NonNull String authorUsername,
        @NonNull String text,
        @NonNull LocalDateTime createdAt) {}
