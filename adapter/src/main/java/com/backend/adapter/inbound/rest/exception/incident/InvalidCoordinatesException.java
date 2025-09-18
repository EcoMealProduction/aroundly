package com.backend.adapter.inbound.rest.exception.incident;

/**
 * Exception thrown when provided coordinates are invalid
 * or cannot be processed.
 */
public class InvalidCoordinatesException extends RuntimeException {
  public InvalidCoordinatesException(String message) {
    super(message);
  }
}
