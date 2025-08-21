package com.backend.services;

import com.backend.port.in.LocationUseCase;
import com.backend.domain.shared.Location;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class LocationService implements LocationUseCase {

    @Override
    public Location save(Location location) {
        return null;
    }

    @Override
    public Location findById(long locationId) {
        return null;
    }

    @Override
    public Location findByCoordinates(double latitude, double longitude) {
        return null;
    }

    @Override
    public Map<Double, Double> sendCoordinates(Location location) {
        return Map.of();
    }

    @Override
    public String detectAddress(double latitude, double longitude) {
        return "";
    }
}
