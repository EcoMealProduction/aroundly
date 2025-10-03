package com.backend.domain.media;

import java.io.InputStream;

/**
 * Represents a media resource that holds information about its type,
 * the associated content type, and the location where it can be accessed.
 *
 * @param size        the size of media
 * @param contentType the content type of the media
 * @param filename    the filename
 */
public record Media(
    long size,
    String filename,
    String contentType) { }
