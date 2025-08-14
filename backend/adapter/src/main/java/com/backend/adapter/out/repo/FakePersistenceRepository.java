package com.backend.adapter.out.repo;

import com.backend.domain.happening.Incident;
import com.backend.domain.happening.metadata.IncidentMetadata;
import com.backend.domain.shared.Location;
import com.backend.port.out.IncidentRepository;
import java.util.Objects;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class FakePersistenceRepository implements IncidentRepository {

    private final Map<Long, Incident> storage = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    @Override
    public Incident save(Incident incident) {
        long id = idGenerator.getAndIncrement();
        storage.put(id, incident);
        return incident;
    }

    @Override
    public Optional<Incident> findById(long id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<Incident> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public List<Incident> findByAllInGivenRange(double lat0, double lon0, double radiusMeters) {
        final double radiusKm = radiusMeters / 1000.0;
        return storage.values().stream()
            .filter(Objects::nonNull)
            .filter(i -> {
                IncidentMetadata metadata = i.metadata();
                if (metadata == null) return false;
                Location loc = metadata.location();
                double lat = loc.latitude().doubleValue();
                double lon = loc.longitude().doubleValue();

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
    public List<Incident> findByUserId(long userId) {
        return storage.values().stream()
                .filter(Objects::nonNull)
                .toList();
    }
}
