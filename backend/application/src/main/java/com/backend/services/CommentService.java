package com.backend.services;

import com.backend.port.in.CommentUseCase;
import com.backend.user.Comment;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService implements CommentUseCase {

    @Override
    public Comment create(Comment comment) {
        return null;
    }

    @Override
    public List<Comment> findByHappeningId(long happeningId) {
        return List.of();
    }

    @Override
    public Comment updated(long id, Comment newComment) {
        return null;
    }

    @Override
    public Comment findById(long id) {
        return null;
    }

    @Override
    public void delete(long id) {

    }
}
