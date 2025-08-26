package com.backend.adapter.in.rest.exceptions;

/**
 * Exception thrown when an actor with the given identifier
 * or criteria cannot be found.
 */
public class ActorNotFoundException extends RuntimeException {
  public ActorNotFoundException(String message) {
    super(message);
  }
}
