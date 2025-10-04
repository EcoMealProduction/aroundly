package com.backend.adapter.outbound.repo.persistence;

import com.backend.domain.actor.Comment;
import com.backend.port.outbound.repo.CommentRepository;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Repository;

@Repository
public class FakeCommentPersistenceRepository implements CommentRepository {

  private final Map<Long, Comment> storage = new ConcurrentHashMap<>();
  private final AtomicLong idGenerator = new AtomicLong(1);

  @Override
  public Comment save(Comment comment) {
    long id = idGenerator.getAndIncrement();
    storage.put(id, comment);

    return comment;
  }

  @Override
  public List<Comment> findByHappeningId(long happeningId) {
    return storage.values().stream()
        .filter(comment -> comment.happeningId().id() == happeningId)
        .toList();
  }

  @Override
  public Comment findById(long id) {
    return storage.get(id);
  }

  @Override
  public void delete(long id) {
    storage.remove(id);
  }

  @Override
  public Comment update(long id, Comment updatedComment) {
    storage.replace(id, updatedComment);
    return updatedComment;
  }
}
