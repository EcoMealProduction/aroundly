package com.backend.port.inbound.commands;

/**
 * Command object representing geographic coordinates.
 *
 * @param lat the latitude value, typically in the range [-90, 90]
 * @param lon the longitude value, typically in the range [-180, 180]
 */
public record CoordinatesCommand(double lat, double lon) {

}
