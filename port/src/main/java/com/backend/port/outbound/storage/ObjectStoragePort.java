package com.backend.port.outbound.storage;

import com.backend.domain.media.Media;
import com.backend.port.inbound.commands.UploadMediaCommand;
import java.time.Duration;
import java.util.Set;

/**
 * Port for interacting with object storage (e.g. MinIO, S3).
 */
public interface ObjectStoragePort {

  /**
   * Uploads multiple media objects.
   *
   * @param uploads media to upload
   * @return stored media with metadata
   */
  Set<Media> uploadAll(Set<UploadMediaCommand> uploads) throws Exception;

  /**
   * Deletes media by their keys.
   *
   * @param keys object keys to delete
   */
  void deleteAllByKeys(Set<String> keys) throws Exception;

  /**
   * Creates a presigned URL for downloading an object.
   *
   * @param key object key
   * @param ttl URL validity duration
   * @return presigned GET URL
   */
  String presignGet(String key, Duration ttl) throws Exception;

  /**
   * Creates a presigned URL for uploading an object.
   *
   * @param key object key
   * @param ttl URL validity duration
   * @return presigned PUT URL
   */
  String presignPut(String key, Duration ttl) throws Exception;
}
