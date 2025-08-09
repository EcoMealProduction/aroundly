package com.backend.services;

import com.backend.domain.happening.Incident;
import com.backend.port.in.ReactionUseCase;
import com.backend.domain.shared.SentimentEngagement;

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
    public Incident confirmIncident(Incident incident) {
        return null;
    }

    @Override
    public Incident denyIncident(Incident incident) {
        return null;
    }
}
