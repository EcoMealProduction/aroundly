package com.backend.port.in;

import com.backend.domain.happening.Incident;
import com.backend.domain.shared.SentimentEngagement;

public interface ReactionUseCase {
    <T> SentimentEngagement addLike(T object);

    <T> SentimentEngagement addDislike(T object);

    Incident confirmIncident(Incident incident);

    Incident denyIncident(Incident incident);
}
