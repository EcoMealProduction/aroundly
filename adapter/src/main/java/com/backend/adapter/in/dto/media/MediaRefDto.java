package com.backend.adapter.in.dto.media;

import java.net.URI;

/**
 * DTO representing a reference to a media resource provided by the client.
 *
 * Contains the URI pointing to the media content, such as an image or video.
 */
public record MediaRefDto(URI uri) { }
