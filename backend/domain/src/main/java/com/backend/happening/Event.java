package com.backend.happening;

import com.backend.shared.Location;
import com.backend.user.Comment;
import lombok.Builder;
import lombok.NonNull;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a public event reported by a user.
 * Includes core details such as title, description, location, time range, user reactions, and comments.
 */
@Builder(toBuilder = true)
public record Event(
        @NonNull String title,
        @NonNull String description,
        @NonNull String authorUsername,
        @NonNull Location location,
        List<Comment> comments,
        int likes,
        int dislikes,
        @NonNull LocalDateTime startTime,
        @NonNull LocalDateTime endTime
) implements Happening{

    /**
     * Constructs an {@code Event} with validation logic for input values.
     *
     * @throws IllegalArgumentException if:
     * <ul>
     *   <li>{@code title} is shorter than 10 characters</li>
     *   <li>{@code description} is shorter than 10 characters</li>
     *   <li>{@code likes} or {@code dislikes} is negative</li>
     *   <li>{@code startTime} or {@code endTime} is in the past</li>
     *   <li>{@code endTime} is less than 30 minutes after {@code startTime}</li>
     * </ul>
     */
    public Event {
        if (title.length() < 10) {
            throw new IllegalArgumentException("Title too short.");
        }

        if (description.length() < 10) {
            throw new IllegalArgumentException("Description too short.");
        }

        if (likes < 0 || dislikes < 0) {
            throw new IllegalArgumentException("Likes and dislikes cannot be negative.");
        }

        if (startTime.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Start time cannot be before now.");
        }

        if (endTime.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("End time cannot be before now.");
        }

        if (endTime.isBefore(startTime.plusMinutes(30))) {
            throw new IllegalArgumentException("Event duration must be at least 30 minutes.");
        }

        comments = comments == null ? List.of() : new ArrayList<>(comments);

    }

    /**
     * Increments the like count.
     *
     * @return a new {@code Event} instance with one more like.
     */
    @Override
    public Event addLike() {
        return this.toBuilder()
                .likes(likes + 1)
                .build();
    }

    /**
     * Increments the dislike count.
     *
     * @return a new {@code Event} instance with one more dislike.
     */
    @Override
    public Event addDislike() {
        return this.toBuilder()
                .dislikes(dislikes + 1)
                .build();
    }

    /**
     * Decrements the like count.
     *
     * @return a new {@code Event} instance with one less like.
     */
    @Override
    public Event removeLike() {
        return this.toBuilder()
                .likes(likes - 1)
                .build();
    }

    /**
     * Decrements the dislike count.
     *
     * @return a new {@code Event} instance with one less dislike.
     */
    @Override
    public Event removeDislike() {
        return this.toBuilder()
                .dislikes(dislikes - 1)
                .build();
    }

    /**
     * Adds a new comment to the event.
     *
     * @param comment the {@code Comment} to be added.
     * @return a new {@code Event} instance with the comment appended.
     */
    @Override
    public Event addComment(Comment comment) {
        List<Comment> commentsCopy = new ArrayList<>(comments());
        commentsCopy.add(comment);
        return toBuilder()
                .comments(commentsCopy)
                .build();
    }

    /**
     * Checks if the event has ended.
     *
     * @return {@code true} if the current time is after the {@code endTime}, {@code false} otherwise.
     */
    public boolean isFinished() {
        return LocalDateTime.now().isAfter(this.endTime);
    }
}
