package com.backend.adapter.in.dto.request;

import java.math.BigDecimal;

/**
 * DTO for receiving location data from the client.
 *
 * Contains latitude, longitude, and an optional address,
 * typically used when creating or updating location records.
 */
public record LocationRequestDto(BigDecimal latitude, BigDecimal longitude, String address) { }
