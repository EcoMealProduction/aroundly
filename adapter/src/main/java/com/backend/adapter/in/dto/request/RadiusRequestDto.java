package com.backend.adapter.in.dto.request;

/**
 * Request DTO for querying locations within a given radius.
 *
 * @param lat    latitude of the center point
 * @param lon    longitude of the center point
 * @param radius search radius in kilometers
 */
public record RadiusRequestDto(double lat, double lon, double radius) { }
