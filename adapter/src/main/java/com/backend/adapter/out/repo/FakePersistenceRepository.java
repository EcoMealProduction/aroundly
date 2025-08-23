package com.backend.adapter.out.repo;

import com.backend.domain.happening.old.OldIncident;
import com.backend.domain.happening.metadata.IncidentMetadata;
import com.backend.domain.old.OldLocation;
import com.backend.port.outbound.IncidentRepository;
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

    private final Map<Long, OldIncident> storage = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    @Override
    public OldIncident save(OldIncident oldIncident) {
        long id = idGenerator.getAndIncrement();
        storage.put(id, oldIncident);
        return oldIncident;
    }

    @Override
    public Optional<OldIncident> findById(long id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<OldIncident> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public List<OldIncident> findByAllInGivenRange(double lat0, double lon0, double radiusMeters) {
        final double radiusKm = radiusMeters / 1000.0;
        return storage.values().stream()
            .filter(Objects::nonNull)
            .filter(i -> {
                IncidentMetadata metadata = i.metadata();
                if (metadata == null) return false;
                OldLocation loc = metadata.oldLocation();
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
    public List<OldIncident> findByUserId(long userId) {
        return storage.values().stream()
                .filter(Objects::nonNull)
                .toList();
    }
}
