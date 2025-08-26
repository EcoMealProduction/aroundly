package com.backend.adapter.in.rest.exceptions;

/**
 * Exception thrown when an incident with the given identifier
 * or criteria cannot be found.
 */
public class IncidentNotFoundException extends RuntimeException {
  public IncidentNotFoundException(String message) {
    super(message);
  }
}
