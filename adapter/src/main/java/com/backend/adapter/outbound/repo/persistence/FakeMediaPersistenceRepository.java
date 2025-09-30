package com.backend.adapter.outbound.repo.persistence;

import com.backend.domain.media.Media;
import com.backend.port.outbound.MediaRepository;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Repository;

@Repository
public class FakeMediaPersistenceRepository implements MediaRepository {

  private final Map<Long, Set<Media>> storage = new ConcurrentHashMap<>();
  private final AtomicLong idGenerator = new AtomicLong(1);

  @Override
  public Media save(Media media) {
    long id = idGenerator.getAndIncrement();
    storage.put(id, Set.of(media));
    return media;
  }

  @Override
  public Set<Media> saveAll(Set<Media> mediaSet) {
    long id = idGenerator.getAndIncrement();
    storage.put(id, mediaSet);
    return mediaSet;
  }

  @Override
  public void delete(long id) {
    storage.remove(id);
  }
}
