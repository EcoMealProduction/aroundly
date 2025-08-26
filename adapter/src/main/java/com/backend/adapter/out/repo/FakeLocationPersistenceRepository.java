package com.backend.adapter.out.repo;

import com.backend.domain.location.Location;
import com.backend.port.outbound.LocationRepository;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Repository;

@Repository
public class FakeLocationPersistenceRepository implements LocationRepository {

  private final Map<Long, Location> storage = new ConcurrentHashMap<>();
  private final AtomicLong idGenerator = new AtomicLong(1);

  @Override
  public Location save(Location location) {
    long id = idGenerator.getAndIncrement();
    storage.put(id, location);

    return location;
  }

  @Override
  public Location findById(long id) {
    return storage.get(id);
  }

  @Override
  public Optional<Location> findByCoordinate(double latitude, double longitude) {
    return storage.values().stream()
        .filter(loc -> loc.latitude() == latitude && loc.longitude() == longitude)
        .findFirst();
  }

  @Override
  public void deleteById(long id) {
    storage.remove(id);
  }
}
