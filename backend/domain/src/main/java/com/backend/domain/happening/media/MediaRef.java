package com.backend.domain.happening.media;

import java.net.URI;

public record MediaRef(
    MediaKind kind,
    String contentType,
    URI uri) { }
