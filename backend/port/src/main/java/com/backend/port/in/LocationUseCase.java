package com.backend.port.in;

import com.backend.shared.Location;
import java.util.Map;

/**
 * Defines use cases for handling location-based operations,
 * such as sending coordinates, saving addresses, and detecting addresses from coordinates.
 */
public interface LocationUseCase {

    /**
     * Saves a new location or updates an existing one in the system.
     *
     * @param location The Location object containing latitude, longitude, and address details.
     * @return The saved Location object, potentially with a generated ID.
     */
    Location save(Location location);

    /**
     * Retrieves a Location by its unique identifier.
     *
     * @param locationId The ID of the location to retrieve.
     * @return The Location object if found, or null if not found.
     */
    Location findById(long locationId);

    /**
     * Finds a Location by its latitude and longitude.
     *
     * @param latitude  The latitude of the location.
     * @param longitude The longitude of the location.
     * @return The Location object matching the provided coordinates, or null if not found.
     */
    Location findByCoordinates(double latitude, double longitude);

    /**
     * Processes or sends the coordinates contained within a Location object.
     * The returned map may represent latitude and longitude values, or any additional processed data.
     *
     * @param location The Location object containing coordinates to be sent or processed.
     * @return A map where the key is latitude and the value is longitude (or vice versa, as per convention).
     */
    Map<Double, Double> sendCoordinates(Location location);

    /**
     * Saves the specified address and returns the corresponding Location object.
     *
     * @param address The human-readable address to be saved.
     * @return The Location object representing the saved address, including coordinates if applicable.
     */
    Location saveAddress(String address);

    /**
     * Detects or resolves a human-readable address from the given latitude and longitude.
     *
     * @param latitude  The latitude coordinate.
     * @param longitude The longitude coordinate.
     * @return The detected human-readable address as a String.
     */
    String detectAddress(double latitude, double longitude);
}
