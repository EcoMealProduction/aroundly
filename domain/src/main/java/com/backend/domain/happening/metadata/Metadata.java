package com.backend.domain.happening.metadata;

import com.backend.domain.happening.old.Event;
import com.backend.domain.happening.old.OldHappening;
import com.backend.domain.happening.old.OldIncident;
import com.backend.domain.media.Media;
import com.backend.domain.old.OldLocation;
import com.backend.domain.actor.Actor;
import java.util.Set;

/**
 * Mixin interface for metadata associated with {@link OldHappening} entities,
 * such as {@link OldIncident} or {@link Event}.
 *
 * This interface is intended to be implemented by types like {@code EventMetadata}
 * or {@code IncidentMetadata} to provide contextual information such as time,
 * location, or reporting details.
 *
 * Serves as a structural marker for polymorphic behavior without enforcing method contracts.
 */
public interface Metadata {
    Actor actor();
    Set<Media> media();
    OldLocation location();
}
