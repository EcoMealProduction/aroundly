package com.backend.services.exceptions;

/**
 * Exception thrown when an incident with the given identifier
 * or criteria cannot be found.
 */
public class IncidentNotFoundException extends IncidentException {

  /**
   * Creates a new exception with the given message.
   *
   * @param message detail message explaining the exception
   */
  public IncidentNotFoundException(String message) {
    super(message);
  }

  /**
   * Creates a new exception with the given message and underlying cause.
   *
   * @param message detail message explaining the exception
   * @param cause   the root cause of this exception
   */
  public IncidentNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }
}
