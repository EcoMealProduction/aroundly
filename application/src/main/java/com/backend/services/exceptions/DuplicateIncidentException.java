package com.backend.services.exceptions;

/**
 * Exception thrown when attempting to create an incident
 * that already exists, resulting in a duplicate.
 */
public class DuplicateIncidentException extends IncidentException {

  /**
   * Creates a new exception with the given message.
   *
   * @param message detail message explaining the exception
   */
  public DuplicateIncidentException(String message) {
    super(message);
  }

  /**
   * Creates a new exception with the given message and underlying cause.
   *
   * @param message detail message explaining the exception
   * @param cause   the root cause of this exception
   */
  public DuplicateIncidentException(String message, Throwable cause) {
    super(message, cause);
  }
}
