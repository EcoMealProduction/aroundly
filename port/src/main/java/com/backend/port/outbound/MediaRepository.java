package com.backend.port.outbound;

import com.backend.domain.media.Media;
import java.util.Set;

/**
 * Repository interface for managing {@link Media} persistence.
 *
 * Provides methods for saving and deleting single or multiple media objects.
 */
public interface MediaRepository {

  /**
   * Saves a single media object.
   *
   * @param media the media to save
   * @return the persisted media
   */
  Media save(Media media);

  /**
   * Saves multiple media objects in bulk.
   *
   * @param mediaSet the set of media objects to save
   * @return the persisted set of media
   */
  Set<Media> saveAll(Set<Media> mediaSet);

  /**
   * Deletes a single media object.
   *
   * @param media the media to delete
   */
  void delete(Media media);

  /**
   * Deletes multiple media objects in bulk.
   *
   * @param mediaSet the set of media objects to delete
   */
  void deleteAll(Set<Media> mediaSet);
}
