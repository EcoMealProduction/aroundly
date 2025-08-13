package com.backend.adapter.out.repo;

import com.backend.domain.happening.Happening;
import com.backend.domain.happening.Incident;
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
    public List<Incident> findByAllInGivenRange(int range) {
        return storage.values().stream().limit(range).toList();
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
