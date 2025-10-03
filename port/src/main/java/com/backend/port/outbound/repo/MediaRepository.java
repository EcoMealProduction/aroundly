package com.backend.port.outbound.repo;

import com.backend.domain.media.Media;
import java.util.Optional;
import java.util.Set;

/**
 * Repository interface for managing {@link Media} in the domain layer.
 */
public interface MediaRepository {

  /**
   * Saves a set of media objects.
   *
   * @param media the media to save
   * @return the saved media with updated state
   * @throws Exception if persistence fails
   */
  Set<Media> saveAll(Set<Media> media) throws Exception;

  /**
   * Deletes all media matching the given keys.
   *
   * @param keys the media keys to delete
   * @throws Exception if persistence fails
   */
  void deleteAllByKeys(Set<String> keys) throws Exception;

  /**
   * Finds a media object by its key.
   *
   * @param key the unique media key
   * @return an {@link Optional} containing the media if found
   * @throws Exception if persistence fails
   */
  Optional<Media> findByKey(String key) throws Exception;
}
