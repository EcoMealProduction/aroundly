package com.backend.services;

import com.backend.domain.location.Location;
import com.backend.port.inbound.LocationUseCase;
import com.backend.port.inbound.commands.CoordinatesCommand;
import com.backend.port.outbound.LocationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Service layer implementation of {@link LocationUseCase},
 * providing access to location data through the repository.
 */
@Service
@AllArgsConstructor
public class LocationService implements LocationUseCase {

    private final LocationRepository locationRepository;

    /**
     * Finds a location by its unique identifier.
     *
     * @param locationId id of the location
     * @return the matching location, or null if not found
     */
    @Override
    public Location findById(long locationId) {
        return locationRepository.findById(locationId);
    }

    /**
     * Finds a location by its latitude and longitude coordinates.
     *
     * @param coordinatesCommand command containing latitude and longitude
     * @return the matching location, or null if not found
     */
    @Override
    public Location findByCoordinates(CoordinatesCommand coordinatesCommand) {
        final double latitude = coordinatesCommand.lat();
        final double longitude = coordinatesCommand.lon();

        return locationRepository.findByCoordinate(latitude, longitude).get();
    }
}
