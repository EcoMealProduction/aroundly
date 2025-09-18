package com.backend.adapter.inbound.rest.exception.incident;

/**
 * Exception thrown when an actor with the given identifier
 * or criteria cannot be found.
 */
public class ActorNotFoundException extends RuntimeException {
  public ActorNotFoundException(String message) {
    super(message);
  }
}
