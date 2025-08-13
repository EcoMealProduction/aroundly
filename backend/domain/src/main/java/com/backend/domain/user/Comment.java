package com.backend.domain.user;

import lombok.Builder;
import lombok.NonNull;

import java.time.LocalDateTime;

/**
 * Represents a user-generated comment associated with a {@code Happening} (such as an Event or Incident).
 * Contains the author's username, the comment content, and the timestamp of creation.
 */
@Builder(toBuilder = true)
public record Comment(
        @NonNull Actor actor,
        @NonNull String text,
        LocalDateTime createdAt) {

    /**
     * Constructs a {@code Comment} instance with input validation.
     *
     *   @throws IllegalArgumentException if:
     *   @param text is empty or contains only whitespace< ; is shorter than 5 characters
     *   @param createdAt is in the future
     */
    public Comment {
        if (text.trim().isEmpty())
            throw new IllegalArgumentException("Text cannot be empty or only spaces.");

        if (text.length() < 5)
            throw new IllegalArgumentException("Text must have at least 5 characters.");

        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }

        if (createdAt.isAfter(LocalDateTime.now()))
            throw new IllegalArgumentException("Comment date cannot be in the future.");
    }
}
