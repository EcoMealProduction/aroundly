package com.backend.services;

import com.backend.port.inbound.CommentUseCase;
import com.backend.domain.actor.Comment;
import com.backend.port.inbound.commands.CommentTextCommand;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService implements CommentUseCase {

    @Override
    public Comment create(CommentTextCommand commentText) {
        return null;
    }

    @Override
    public List<Comment> findByActorId(long actorId) {
        return List.of();
    }

    @Override
    public List<Comment> findByHappeningId(long happeningId) {
        return List.of();
    }

    @Override
    public Comment update(long commentId, CommentTextCommand newCommentText) {
        return null;
    }

    @Override
    public void delete(long commentId) {

    }
}
