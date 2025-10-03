package com.backend.port.outbound.repo;

import com.backend.domain.location.LocationId;

/**
 * Port for supplying unique {@link LocationId} values.
 */
public interface LocationIdGenerator {

  /**
   * Returns the next unique {@link LocationId}.
   */
  LocationId nextId();
}
