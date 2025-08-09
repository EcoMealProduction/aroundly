package com.backend.port.out;

import com.backend.domain.shared.SentimentEngagement;

public interface ReactionRepository {
    SentimentEngagement save(SentimentEngagement sentimentEngagement);
    SentimentEngagement update(SentimentEngagement sentimentEngagement);
    SentimentEngagement delete(SentimentEngagement sentimentEngagement);
}
