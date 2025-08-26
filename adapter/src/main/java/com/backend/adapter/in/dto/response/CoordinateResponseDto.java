package com.backend.adapter.in.dto.response;

/**
 * Response DTO carrying coordinate details.
 *
 * @param lat     latitude of the coordinate
 * @param lon     longitude of the coordinate
 * @param address human-readable formatted address
 */
public record CoordinateResponseDto(double lat, double lon, String address) { }
