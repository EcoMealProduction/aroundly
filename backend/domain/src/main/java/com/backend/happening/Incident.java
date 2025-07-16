package com.backend.happening;

import com.backend.shared.Location;
import com.backend.user.Comment;
import lombok.Builder;
import lombok.NonNull;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a user-reported incident (e.g., traffic, danger, or other local issues).
 * Contains information about the incident's details, location, timing, and public feedback such as likes, dislikes, and comments.
 */
@Builder(toBuilder = true)
public record Incident(
        @NonNull String title,
        @NonNull String description,
        @NonNull String authorUsername,
        @NonNull Location location,
        LocalDateTime createdAt,
        LocalDateTime expirationTime,
        List<Comment> comments,
        int likes,
        int dislikes,
        int confirms,
        int denies,
        int consecutiveDenies
) implements Happening{

    /**
     * Constructs an {@code Incident} instance with input validation.
     *
     * @throws IllegalArgumentException if:
     *   @param title is shorter than 10 characters
     *   @param description is shorter than 20 characters
     *   Any reaction count is negative
     *   @param expirationTime is before @param createdAt or before current time
     */
    public Incident {
        if (title.length() < 10)
            throw new IllegalArgumentException("Title too short.");

        if (description.length() < 20)
            throw new IllegalArgumentException("Description too short.");

        if (likes < 0 || dislikes < 0 || confirms < 0 || denies < 0 || consecutiveDenies < 0)
            throw new IllegalArgumentException("Negative values not allowed.");

        if (createdAt == null)
            createdAt = LocalDateTime.now();

        if (expirationTime == null)
            expirationTime = createdAt.plusMinutes(30);

        if (expirationTime.isBefore(createdAt))
            throw new IllegalArgumentException("Expiration time cannot be before createdAt.");

        if (expirationTime.isBefore(LocalDateTime.now()))
            throw new IllegalArgumentException("Expiration time cannot be before now.");

        comments = comments == null ? List.of() : new ArrayList<>(comments);
    }

    /**
     * Checks whether the incident should be deleted.
     * An incident is deleted if it is expired or has at least 3 consecutive denies.
     *
     * @return {@code true} if the incident should be removed.
     */
    public boolean isDeleted() {
            return consecutiveDenies >=3 || isExpired();
    }

    /**
     * Adds a confirmation to the incident.
     * This increases the confirmation count, extends the expiration time by 5 minutes,
     * and resets the consecutive denies counter.
     *
     * @return A new {@code Incident} instance with updated state.
     */
    public Incident addConfirm() {
        return this.toBuilder()
                .confirms(confirms + 1)
                .expirationTime(expirationTime.plusMinutes(5))
                .consecutiveDenies(0)
                .build();
    }

    /**
     * Adds a denial to the incident.
     * This increases both the total and consecutive denial counters.
     *
     * @return A new {@code Incident} instance with updated denial counts.
     */
    public Incident addDeny() {
        return this.toBuilder()
                .denies(denies + 1)
                .consecutiveDenies(consecutiveDenies + 1)
                .build();
    }

    /**
     * Checks if the incident has expired based on the current time.
     *
     * @return {@code true} if the expiration time is in the past.
     */
    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expirationTime);
    }

    /**
     * Increments the like counter.
     *
     * @return A new {@code Incident} instance with one additional like.
     */
    @Override
    public Incident addLike() {
        return toBuilder()
                .likes(likes + 1)
                .build();
    }

    /**
     * Decrements the like counter.
     *
     * @return A new {@code Incident} instance with one less like.
     */
    @Override
    public Incident removeLike() {
        return toBuilder()
                .likes(likes - 1)
                .build();
    }

    /**
     * Increments the dislike counter.
     *
     * @return A new {@code Incident} instance with one additional dislike.
     */
    @Override
    public Incident addDislike() {
        return toBuilder()
                .dislikes(dislikes + 1)
                .build();
    }

    /**
     * Decrements the dislike counter.
     *
     * @return A new {@code Incident} instance with one less dislike.
     */
    @Override
    public Incident removeDislike() {
        return toBuilder()
                .dislikes(dislikes - 1)
                .build();
    }

    /**
     * Appends a new comment to the incident's comment list.
     *
     * @param comment The {@code Comment} to be added.
     * @return A new {@code Incident} instance with the added comment.
     */
    @Override
    public Incident addComment(Comment comment) {
        List<Comment> commentsCopy = new  ArrayList<>(comments());
        commentsCopy.add(comment);

        return toBuilder()
                .comments(commentsCopy)
                .build();
    }
}
