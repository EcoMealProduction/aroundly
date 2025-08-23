package com.backend.services;

import com.backend.domain.happening.old.OldIncident;
import com.backend.domain.reactions.SentimentEngagement;
import com.backend.port.in.ReactionUseCase;

public class ReactionService implements ReactionUseCase {

    @Override
    public <T> SentimentEngagement addLike(T object) {
        return null;
    }

    @Override
    public <T> SentimentEngagement addDislike(T object) {
        return null;
    }

    @Override
    public OldIncident confirmIncident(OldIncident oldIncident) {
        return null;
    }

    @Override
    public OldIncident denyIncident(OldIncident oldIncident) {
        return null;
    }
}
