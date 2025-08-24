package com.backend.services;

import com.backend.domain.happening.old.OldIncident;
import com.backend.domain.reactions.SentimentEngagement;
import com.backend.port.inbound.ReactionUseCase;
import com.backend.port.inbound.commands.ReactionSummary;

public class ReactionService implements ReactionUseCase {

    @Override
    public ReactionSummary addLike() {
        return null;
    }

    @Override
    public ReactionSummary addDislike() {
        return null;
    }

    @Override
    public ReactionSummary removeLike() {
        return null;
    }

    @Override
    public ReactionSummary removeDislike() {
        return null;
    }
}
