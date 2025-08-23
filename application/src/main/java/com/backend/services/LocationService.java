package com.backend.services;

import com.backend.domain.old.OldLocation;
import com.backend.port.inbound.LocationUseCase;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class LocationService implements LocationUseCase {

    @Override
    public OldLocation save(OldLocation oldLocation) {
        return null;
    }

    @Override
    public OldLocation findById(long locationId) {
        return null;
    }

    @Override
    public OldLocation findByCoordinates(double latitude, double longitude) {
        return null;
    }

    @Override
    public Map<Double, Double> sendCoordinates(OldLocation oldLocation) {
        return Map.of();
    }

    @Override
    public String detectAddress(double latitude, double longitude) {
        return "";
    }
}
