package com.backend.adapter.inbound.dto.media;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * DTO representing a reference to a media resource provided by the client.
 */
@Schema(description = "Media reference containing the URI to access the media file")
public record MediaDto(
    @Schema(
        description = "the name of file",
        example = "https://api.example.com/media/images/incident_photo_123.jpg"
    )
    String filename) { }
