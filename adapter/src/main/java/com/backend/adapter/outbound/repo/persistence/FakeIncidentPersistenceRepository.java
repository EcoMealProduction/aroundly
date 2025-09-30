package com.backend.adapter.outbound.repo.persistence;

import com.backend.domain.happening.Happening;
import com.backend.domain.happening.Incident;
import com.backend.domain.location.Location;
import com.backend.domain.location.LocationId;
import com.backend.port.outbound.IncidentRepository;
import com.backend.port.outbound.LocationRepository;

import java.util.Objects;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class FakeIncidentPersistenceRepository implements IncidentRepository {

    private final LocationRepository locationRepository;
    private final Map<Long, Happening> storage = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public FakeIncidentPersistenceRepository(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    @Override
    public Incident save(Incident incident) {
        long id = idGenerator.getAndIncrement();
        storage.put(id, incident);
        return incident;
    }

    @Override
    public Optional<Happening> findById(long id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public boolean existsById(long happeningId) {
      return storage.get(happeningId) != null;
    }

    @Override
    public List<Incident> findAllInGivenRange(double lat0, double lon0, double radiusMeters) {
        final double radiusKm = radiusMeters / 1000.0;
        return storage.values().stream()
            .filter(h -> h instanceof Incident)
            .map(Incident.class::cast)
            .filter(i -> {
                LocationId locationId = i.locationId();
                Location location = locationRepository.findById(locationId.value());
                if (location == null) return false;
                
                double lat = location.latitude();
                double lon = location.longitude();

                return haversineKm(lat0, lon0, lat, lon) <= radiusKm;
            })
            .toList();
    }

    private static double haversineKm(double lat1, double lon1, double lat2, double lon2) {
        final double R = 6371.0; // km
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat/2) * Math.sin(dLat/2)
            + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
            * Math.sin(dLon/2) * Math.sin(dLon/2);
        return 2 * R * Math.asin(Math.sqrt(a));
    }

    @Override
    public void deleteById(long id) {
        storage.remove(id);
    }

    @Override
    public List<Happening> findByUserId(String userId) {
        return storage.values().stream()
                .filter(Objects::nonNull)
                .toList();
    }
}
