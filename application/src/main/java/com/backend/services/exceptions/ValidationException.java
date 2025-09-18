package com.backend.services.exceptions;

/**
 * Exception thrown when provided input data fails validation
 * or does not meet required constraints.
 */
public class ValidationException extends IncidentException {

  /**
   * Creates a new validation exception with the given message.
   *
   * @param message detail message explaining the validation error
   */
  public ValidationException(String message) {
    super(message);
  }

  /**
   * Creates a new validation exception with the given message and underlying cause.
   *
   * @param message detail message explaining the validation error
   * @param cause   the root cause of this exception
   */
  public ValidationException(String message, Throwable cause) {
    super(message, cause);
  }
}
