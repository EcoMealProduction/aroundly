package com.backend.user;

import lombok.Builder;
import lombok.NonNull;

import java.time.LocalDateTime;

/**
 * Represents a user-generated comment associated with a {@code Happening} (such as an Event or Incident).
 * Contains the author's username, the comment content, and the timestamp of creation.
 */
@Builder(toBuilder = true)
public record Comment(
        @NonNull String authorUsername,
        @NonNull String text,
        @NonNull LocalDateTime createdAt
        ) {

    /**
     * Constructs a {@code Comment} instance with input validation.
     *
     * @throws IllegalArgumentException if:
     * <ul>
     *   <li>{@code text} is empty or contains only whitespace</li>
     *   <li>{@code text} is shorter than 5 characters</li>
     *   <li>{@code createdAt} is in the future</li>
     * </ul>
     */
    public Comment {
        if (text.trim().isEmpty()) {
            throw new IllegalArgumentException("Text cannot be empty or only spaces.");
        }

        if (text.length() < 5) {
            throw new IllegalArgumentException("Text must have at least 5 characters.");
        }

        if (createdAt.isAfter(LocalDateTime.now())) {
            throw new IllegalArgumentException("Comment date cannot be in the future.");
        }
    }
}
