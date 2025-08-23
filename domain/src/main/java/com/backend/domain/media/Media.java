package com.backend.domain.media;

import java.net.URI;

/**
 * Represents a media resource that holds information about its type,
 * the associated content type, and the location where it can be accessed.
 *
 * @param kind        the type of media
 * @param contentType the content type of the media
 * @param uri         the location of the media
 */
public record Media(
    MediaKind kind,
    String contentType,
    URI uri) { }
