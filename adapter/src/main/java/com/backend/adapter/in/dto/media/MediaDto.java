package com.backend.adapter.in.dto.media;

import io.swagger.v3.oas.annotations.media.Schema;
import java.net.URI;

/**
 * DTO representing a reference to a media resource provided by the client.
 *
 * Contains the URI pointing to the media content, such as an image or video.
 */
@Schema(description = "Media reference containing the URI to access the media file")
public record MediaDto(
    @Schema(
        description = "URI pointing to the media file location",
        example = "https://api.example.com/media/images/incident_photo_123.jpg"
    )
    URI uri) { }
