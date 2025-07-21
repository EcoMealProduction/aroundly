package com.backend.happening;

import com.backend.happening.metadata.IncidentMetadata;
import com.backend.happening.metadata.Metadata;
import com.backend.shared.SentimentEngagement;
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
public record Incident(
        @NonNull String title,
        @NonNull String description,
        List<Comment> comments,
        IncidentMetadata metadata,
        SentimentEngagement sentimentEngagement,
        IncidentEngagementStats incidentEngagementStats) implements Happening {

    /**
     * Constructs an {@code Incident} instance with input validation.
     *
     * @throws IllegalArgumentException if:
     *   @param title is shorter than 10 characters
     *   @param description is shorter than 20 characters
     */
    public Incident {
        if (title.length() < 10)
            throw new IllegalArgumentException("Title too short.");

        if (description.length() < 20)
            throw new IllegalArgumentException("Description too short.");

        comments = comments == null ? List.of() : new ArrayList<>(comments);
    }

    @Override
    public Builder toBuilder() {
        return new Builder()
                .title(title)
                .description(description)
                .comments(comments)
                .metadata(metadata)
                .sentimentEngagement(sentimentEngagement)
                .incidentEngagementStats(incidentEngagementStats);
    }

    public static class Builder extends Happening.Builder<Builder> {
        protected IncidentEngagementStats incidentEngagementStats;

        @Override
        Builder self() {
            return (Builder) this;
        }

        @Override
        public Builder title(String aTitle) {
            this.title = aTitle;
            return self();
        }

        @Override
        public Builder description(String aDescription) {
            this.description = aDescription;
            return self();
        }

        @Override
        public Builder comments(List<Comment> aComments) {
            this.comments = aComments;
            return self();
        }

        public Builder metadata(IncidentMetadata aIncidentMetadata) {
            this.metadata = aIncidentMetadata;
            return self();
        }

        @Override
        public Builder sentimentEngagement(SentimentEngagement aSentimentEngagement) {
            this.sentimentEngagement = aSentimentEngagement;
            return self();
        }

        public Builder incidentEngagementStats(IncidentEngagementStats aIncidentEngagementStats) {
            this.incidentEngagementStats = aIncidentEngagementStats;
            return self();
        }

        @Override
        public Incident build() {
            return new Incident(
                    title,
                    description,
                    comments,
                    (IncidentMetadata) metadata,
                    sentimentEngagement,
                    incidentEngagementStats);
        }
    }

    /**
     * Checks whether the incident should be deleted.
     * An incident is deleted if it is expired or has at least 3 consecutive denies.
     *
     * @return {@code true} if the incident should be removed.
     */
    public boolean isDeleted() {
            return this.incidentEngagementStats().consecutiveDenies() >=3 || isExpired();
    }

    /**
     * Adds a confirmation to the incident.
     * This increases the confirmation count, extends the expiration time by 5 minutes,
     * and resets the consecutive denies counter.
     *
     * @return A new {@code Incident} instance with updated state.
     */
    public Incident confirmIncident() {
        final IncidentMetadata increasedExpirationTime = metadata.toBuilder()
                .expirationTime(metadata.expirationTime().plusMinutes(5))
                .build();

        final IncidentEngagementStats updatedEngagementStats = incidentEngagementStats.toBuilder()
                .confirms(incidentEngagementStats().confirms() + 1)
                .consecutiveDenies(0)
                .build();

        return this.toBuilder()
                .incidentEngagementStats(updatedEngagementStats)
                .metadata(increasedExpirationTime)
                .build();
    }

    /**
     * Adds a denial to the incident.
     * This increases both the total and consecutive denial counters.
     *
     * @return A new {@code Incident} instance with updated denial counts.
     */
    public Incident denyIncident() {
        final IncidentEngagementStats updatedEngagementStats = incidentEngagementStats.toBuilder()
                .denies(incidentEngagementStats().denies() + 1)
                .consecutiveDenies(incidentEngagementStats.consecutiveDenies() + 1)
                .build();

        return this.toBuilder()
                .incidentEngagementStats(updatedEngagementStats)
                .build();
    }

    /**
     * Checks if the incident has expired based on the current time.
     *
     * @return {@code true} if the expiration time is in the past.
     */
    public boolean isExpired() {
        return LocalDateTime.now().isAfter(metadata.expirationTime());
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
