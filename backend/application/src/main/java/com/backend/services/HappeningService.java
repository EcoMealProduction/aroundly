package com.backend.services;

import com.backend.happening.Happening;
import com.backend.happening.Incident;
import com.backend.port.in.HappeningUseCase;
import com.backend.port.out.HappeningRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HappeningService implements HappeningUseCase {

    private final HappeningRepository happeningRepository;

    public HappeningService(HappeningRepository happeningRepository) {
        this.happeningRepository = happeningRepository;
    }

    @Override
    public List<Happening> findAll() {
        return List.of();
    }

    @Override
    public List<Happening> findAllInGivenRange(int range) {
        return List.of();
    }

    @Override
    public Optional<Happening> findById(long happeningId) {
        return Optional.empty();
    }

    @Override
    public Happening create(Happening happening) {
        return null;
    }

    @Override
    public Happening updated(long id, Happening newHappening) {
        return null;
    }

    @Override
    public int setIncidentVisibilityRange(long incidentId, int range) {
        return 0;
    }

    @Override
    public Incident extendIncidentLifespan(long incidentId) {
        return null;
    }

    @Override
    public void delete(long incidentId) {

    }
}
