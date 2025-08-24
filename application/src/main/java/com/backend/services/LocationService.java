package com.backend.services;

import com.backend.domain.location.Location;
import com.backend.port.inbound.LocationUseCase;
import com.backend.port.inbound.commands.CoordinatesCommand;
import com.backend.port.outbound.LocationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LocationService implements LocationUseCase {

    private final LocationRepository locationRepository;

    @Override
    public Location findById(long locationId) {
        return locationRepository.findById(locationId);
    }

    @Override
    public Location findByCoordinates(CoordinatesCommand coordinatesCommand) {
        final double latitude = coordinatesCommand.lat();
        final double longitude = coordinatesCommand.lon();

        return locationRepository.findByCoordinate(latitude, longitude);
    }
}
