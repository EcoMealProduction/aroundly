package com.backend.services.exceptions;

/**
 * Exception thrown when provided coordinates are invalid,
 * out of range, or cannot be processed.
 */
public class InvalidCoordinatesException extends IncidentException {

  /**
   * Creates a new invalid coordinates exception with the given message.
   *
   * @param message detail message explaining why the coordinates are invalid
   */
  public InvalidCoordinatesException(String message) {
    super(message);
  }

  /**
   * Creates a new invalid coordinates exception with the given message
   * and underlying cause.
   *
   * @param message detail message explaining why the coordinates are invalid
   * @param cause   the root cause of this exception
   */
  public InvalidCoordinatesException(String message, Throwable cause) {
    super(message, cause);
  }
}
