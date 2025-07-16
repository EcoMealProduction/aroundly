package com.backend.happening;

import com.backend.shared.Location;
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
     * @return The authorUsername of the person who reported the happening.
     */
    String authorUsername();

    /**
     * @return The geographical location where the happening occurred.
     */
    Location location();

    /**
     * @return A list of user-submitted comments associated with this happening.
     */
    List<Comment> comments();

    /**
     * @return The number of positive reactions (likes) associated with this happening.
     */
    int likes();

    /**
     * @return The number of negative reactions (dislikes) associated with this happening.
     */
    int dislikes();

    /**
     * Adds a like to the current happening.
     *
     * @return A new instance of the happening with the updated like count.
     */
    Happening addLike();

    /**
     * Removes a like from the current happening.
     *
     * @return A new instance of the happening with the updated like count.
     */
    Happening removeLike();

    /**
     * Adds a dislike to the current happening.
     *
     * @return A new instance of the happening with the updated dislike count.
     */
    Happening addDislike();


    /**
     * Removes a dislike from the current happening.
     *
     * @return A new instance of the happening with the updated dislike count.
     */
    Happening removeDislike();

    /**
     * Adds a user comment to the current happening.
     *
     * @param comment The comment to be added.
     * @return A new instance of the happening with the comment appended.
     */
    Happening addComment(Comment comment);
}
