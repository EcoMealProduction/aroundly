package com.backend.happening;

import com.backend.happening.metadata.Metadata;
import com.backend.shared.SentimentEngagement;
import com.backend.user.Comment;

import java.util.List;

/**
 * Represents a generic reported occurrence within the application (e.g., an {@link Incident} or an {@link Event}).
 * This abstraction defines the common structure and behaviors for all reportable happenings in the system.
 */
public interface Happening {

    /**
     * @return The title of the happening. Should be concise but descriptive.
     */
    String title();

    /**
     * @return A detailed textual description of the happening.
     */
    String description();

    /**
     * @return A list of user-submitted comments associated with this happening.
     */
    List<Comment> comments();

    /**
     * @return An Object of metadata associated with this happening.
     */
    Metadata metadata();

    /**
     * @return The number of positive reactions (likes) associated with this happening.
     */
    SentimentEngagement sentimentEngagement();

    /**
     * Adds a user comment to the current happening.
     *
     * @param comment The comment to be added.
     * @return A new instance of the happening with the comment appended.
     */
    Happening addComment(Comment comment);

    /**
     * Creates a builder that is pre-populated with the current object's field value.
     */
    Builder<?> toBuilder();

    public abstract static class Builder<T extends Builder<T>> {

        protected String title;
        protected String description;
        protected List<Comment> comments;
        protected Metadata metadata;
        protected SentimentEngagement sentimentEngagement;

        abstract T self();

        public T title(String aTitle) {
            this.title = aTitle;
            return self();
        }

        public T description(String aDescription) {
            this.description = aDescription;
            return self();
        }

        public T comments(List<Comment> aComments) {
            this.comments = aComments;
            return self();
        }

        public T metadata(Metadata aMetadata) {
            this.metadata = aMetadata;
            return self();
        }

        public T sentimentEngagement(SentimentEngagement aSentimentEngagement) {
            this.sentimentEngagement = aSentimentEngagement;
            return self();
        }

        public abstract Happening build();
    }
}
