package com.backend.adapter.in.rest.exceptions;

/**
 * Exception thrown when provided coordinates are invalid
 * or cannot be processed.
 */
public class InvalidCoordinatesException extends RuntimeException {
  public InvalidCoordinatesException(String message) {
    super(message);
  }
}
