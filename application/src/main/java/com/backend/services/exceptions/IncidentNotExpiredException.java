package com.backend.services.exceptions;

/**
 * Exception thrown when an attempt is made to delete
 * an incident that has not yet expired.
 */
public class IncidentNotExpiredException extends IncidentException {

  /**
   * Creates a new exception with the given message.
   *
   * @param message detail message explaining the error
   */
  public IncidentNotExpiredException(String message) {
    super(message);
  }

  /**
   * Creates a new exception with the given message and underlying cause.
   *
   * @param message detail message explaining the error
   * @param cause   the root cause of this exception
   */
  public IncidentNotExpiredException(String message, Throwable cause) {
    super(message, cause);
  }
}
