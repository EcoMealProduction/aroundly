package com.backend.port.out;

import com.backend.shared.Location;

import java.util.Optional;

public interface LocationRepository {
    Location save(Location location);
    Optional<Location> findById(long id);
    Optional<Location> findByCoordinate(double latitude, double longitude);
    void deleteById(long id);
}
