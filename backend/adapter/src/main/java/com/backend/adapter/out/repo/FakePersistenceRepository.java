package com.backend.adapter.out.repo;

import com.backend.domain.happening.Happening;
import com.backend.domain.happening.Incident;
import com.backend.port.out.HappeningRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class FakePersistenceRepository implements HappeningRepository {

    private final Map<Long, Happening> storage = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    @Override
    public Happening save(Happening happening) {
        long id = idGenerator.getAndIncrement();
        storage.put(id, happening);
        return happening;
    }

    @Override
    public Optional<Happening> findById(long id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<Happening> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public List<Happening> findByAllInGivenRange(int range) {
        return storage.values().stream().limit(range).toList();
    }

    @Override
    public void deleteById(long id) {
        storage.remove(id);
    }

    @Override
    public List<Happening> findByUserId(long userId) {
        return storage.values().stream()
                .filter(h -> h instanceof Incident)
                .toList();
    }
}
