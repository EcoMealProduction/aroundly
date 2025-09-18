package com.backend.services.exceptions;

/**
 * Exception thrown when an actor with the given identifier
 * or criteria cannot be found.
 */
public class ActorNotFoundException extends IncidentException {

  /**
   * Creates a new exception with the given message.
   *
   * @param message detail message explaining the exception
   */
  public ActorNotFoundException(String message) {
    super(message);
  }

  /**
   * Creates a new exception with the given message and underlying cause.
   *
   * @param message detail message explaining the exception
   * @param cause   the root cause of this exception
   */
  public ActorNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }
}
