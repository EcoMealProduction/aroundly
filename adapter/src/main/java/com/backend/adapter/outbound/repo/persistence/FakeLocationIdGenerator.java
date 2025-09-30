package com.backend.adapter.outbound.repo.persistence;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Component;

import com.backend.domain.location.LocationId;
import com.backend.port.outbound.LocationIdGenerator;

/**
 * In-memory implementation that hands out sequential {@link LocationId}s.
 */
@Component
public class FakeLocationIdGenerator implements LocationIdGenerator {

  private final AtomicLong counter = new AtomicLong(1);

  @Override
  public LocationId nextId() {
    return new LocationId(counter.getAndIncrement());
  }
}
