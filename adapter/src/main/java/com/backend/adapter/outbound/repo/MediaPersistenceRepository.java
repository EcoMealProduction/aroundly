package com.backend.adapter.outbound.repo;

import com.backend.adapter.outbound.entity.MediaEntity;
import java.util.Collection;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data repository for {@link MediaEntity}.
 */
public interface MediaPersistenceRepository extends JpaRepository<MediaEntity, Long> {

  /**
   * Finds a media entity by its key.
   *
   * @param key the media key
   * @return an {@link Optional} containing the entity if found
   */
  Optional<MediaEntity> findByKey(String key);

  /**
   * Deletes all media entities matching the given keys.
   *
   * @param keys the collection of media keys
   */
  void deleteByKeyIn(Collection<String> keys);
}
