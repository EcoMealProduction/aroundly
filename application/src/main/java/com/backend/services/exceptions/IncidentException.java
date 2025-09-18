package com.backend.services.exceptions;

/**
 * Base class for all incident-related runtime exceptions.
 * Provides constructors for message-only and message-with-cause cases.
 */
public abstract class IncidentException extends RuntimeException {

  /**
   * Creates a new incident exception with the given message.
   *
   * @param message detail message explaining the exception
   */
  public IncidentException(String message) {
    super(message);
  }

  /**
   * Creates a new incident exception with the given message and underlying cause.
   *
   * @param message detail message explaining the exception
   * @param cause   the root cause of this exception
   */
  public IncidentException(String message, Throwable cause) {
    super(message, cause);
  }
}
