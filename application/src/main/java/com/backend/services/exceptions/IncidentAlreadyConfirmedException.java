package com.backend.services.exceptions;

/**
 * Exception thrown when an attempt is made to confirm
 * an incident that has already been confirmed.
 */
public class IncidentAlreadyConfirmedException extends IncidentException {

  /**
   * Creates a new exception with the given message.
   *
   * @param message detail message explaining the error
   */
  public IncidentAlreadyConfirmedException(String message) {
    super(message);
  }

  /**
   * Creates a new exception with the given message and underlying cause.
   *
   * @param message detail message explaining the error
   * @param cause   the root cause of this exception
   */
  public IncidentAlreadyConfirmedException(String message, Throwable cause) {
    super(message, cause);
  }
}
