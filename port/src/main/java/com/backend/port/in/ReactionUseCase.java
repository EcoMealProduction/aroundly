package com.backend.port.in;

import com.backend.domain.happening.old.OldIncident;
import com.backend.domain.reactions.SentimentEngagement;

public interface ReactionUseCase {
    <T> SentimentEngagement addLike(T object);

    <T> SentimentEngagement addDislike(T object);

    OldIncident confirmIncident(OldIncident oldIncident);

    OldIncident denyIncident(OldIncident oldIncident);
}
