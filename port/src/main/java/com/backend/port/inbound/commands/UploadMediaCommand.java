package com.backend.port.inbound.commands;

import java.io.InputStream;

/**
 * Command object for uploading a media file.
 *
 * @param stream      file content
 * @param filename    original file name
 * @param size        file size in bytes
 * @param contentType MIME type of the file
 */
public record UploadMediaCommand(
    InputStream stream,
    String filename,
    long size,
    String contentType) { }
