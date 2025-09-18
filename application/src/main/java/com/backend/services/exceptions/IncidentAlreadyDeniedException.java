package com.backend.services.exceptions;

/**
 * Exception thrown when an attempt is made to deny
 * an incident that has already been denied.
 */
public class IncidentAlreadyDeniedException extends IncidentException {

  /**
   * Creates a new exception with the given message.
   *
   * @param message detail message explaining the error
   */
  public IncidentAlreadyDeniedException(String message) {
    super(message);
  }

  /**
   * Creates a new exception with the given message and underlying cause.
   *
   * @param message detail message explaining the error
   * @param cause   the root cause of this exception
   */
  public IncidentAlreadyDeniedException(String message, Throwable cause) {
    super(message, cause);
  }
}
