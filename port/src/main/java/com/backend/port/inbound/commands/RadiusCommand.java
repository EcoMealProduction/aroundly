package com.backend.port.inbound.commands;

/**
 * Command object used for geographic queries within a radius.
 *
 * @param lat    the latitude of the center point
 * @param lon    the longitude of the center point
 * @param radius the search radius in meters
 */
public record RadiusCommand(double lat, double lon, int radius) {

}
