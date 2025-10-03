package com.backend.port.inbound;

import java.time.Duration;

/**
 * Use case for generating presigned URLs for object storage.
 */
public interface ObjectStorageUseCase {

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
