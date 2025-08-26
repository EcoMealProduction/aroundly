package com.backend.adapter.in.dto.request;

/**
 * Request DTO for providing coordinate data.
 *
 * @param lat latitude of the coordinate
 * @param lon longitude of the coordinate
 */
public record CoordinatesRequestDto(double lat, double lon) { }
