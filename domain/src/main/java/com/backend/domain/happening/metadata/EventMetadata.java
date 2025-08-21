package com.backend.domain.happening.metadata;

import com.backend.domain.happening.media.MediaRef;
import com.backend.domain.shared.Location;
import com.backend.domain.user.Actor;
import java.util.Set;
import lombok.Builder;
import lombok.NonNull;

import java.time.LocalDateTime;

/**
 * Metadata for a scheduled event.
 * This record captures who created the event, where it's taking place,
 * and its planned time window (start and end).
 */
@Builder(toBuilder = true)
public record EventMetadata(
    @NonNull Actor actor,
    @NonNull Location location,
    Set<MediaRef> media,
    @NonNull LocalDateTime startTime,
    @NonNull LocalDateTime endTime) implements Metadata {

    /**
     * Compact constructor with validation logic to ensure temporal consistency.
     *
     * @throws IllegalArgumentException if any of the constraints are violated.
     */
    public EventMetadata {
        if (startTime.isBefore(LocalDateTime.now()))
            throw new IllegalArgumentException("Start time cannot be before now.");

        if (endTime.isBefore(LocalDateTime.now()))
            throw new IllegalArgumentException("End time cannot be before now.");

        if (endTime.isBefore(startTime.plusMinutes(30)))
            throw new IllegalArgumentException("Event duration must be at least 30 minutes.");
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
