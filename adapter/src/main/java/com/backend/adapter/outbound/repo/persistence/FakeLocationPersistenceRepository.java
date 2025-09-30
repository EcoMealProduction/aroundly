package com.backend.adapter.outbound.repo;

import com.backend.domain.location.Location;
import com.backend.port.outbound.LocationRepository;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Repository;

@Repository
public class FakeLocationPersistenceRepository implements LocationRepository {

  private final Map<Long, Location> storage = new ConcurrentHashMap<>();

  @Override
  public Location save(Location location) {
    storage.put(location.id().value(), location);
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
