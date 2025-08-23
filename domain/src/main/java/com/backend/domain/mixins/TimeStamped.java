package com.backend.domain.mixins;

import java.time.Instant;

/**
 * Mixin interface for objects that can provide a creation timestamp.
 */
public interface TimeStamped {

  /**
   * Returns the creation timestamp of the object.
   *
   * @return the creation timestamp
   */
  Instant createdAt();
}
