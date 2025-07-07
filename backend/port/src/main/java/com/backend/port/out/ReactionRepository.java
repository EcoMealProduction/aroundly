package com.backend.port.out;

import com.backend.user.Reaction;

import java.util.List;
import java.util.Optional;

public interface ReactionRepository {

    Reaction save(Reaction reaction);

    List<Reaction> findByCommentId(long commentId);

    List<Reaction> findByHappeningId(long happeningId);

    Optional<Reaction> findById(long reactionId);

    void deleteById(long id);
}
