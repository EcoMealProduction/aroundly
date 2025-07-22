package com.backend.happening;

import com.backend.happening.metadata.EventMetadata;
import com.backend.shared.SentimentEngagement;
import com.backend.user.Comment;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a public event reported by a user.
 * Includes core details such as title, description, location, time range, user reactions, and comments.
 */
public record Event(
        @NonNull String title,
        @NonNull String description,
        List<Comment> comments,
        SentimentEngagement sentimentEngagement,
        EventMetadata metadata) implements Happening {

    /**
     * Constructs an {@code Event} with validation logic for input values.
     *
     * @throws IllegalArgumentException if:
     *   @param title is shorter than 10 characters
     *   @param description is shorter than 10 characters
     */
    public Event {
        if (title.length() < 10)
            throw new IllegalArgumentException("Title too short.");

        if (description.length() < 10)
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
                .sentimentEngagement(sentimentEngagement);
    }

    public static class Builder extends Happening.Builder<Builder> {
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

        public Builder metadata(EventMetadata aEventMetadata) {
            this.metadata = aEventMetadata;
            return self();
        }

        @Override
        public Builder sentimentEngagement(SentimentEngagement aSentimentEngagement) {
            this.sentimentEngagement = aSentimentEngagement;
            return self();
        }

        @Override
        public Event build() {
            return new Event(
                    title,
                    description,
                    comments,
                    sentimentEngagement,
                    (EventMetadata) metadata);
        }
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
}
