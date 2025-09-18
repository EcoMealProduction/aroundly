package com.backend.adapter.inbound.rest.exception.incident;

/**
 * Exception thrown when a user attempts to delete
 * an incident that has not yet expired.
 */
public class IncidentNotExpiredException extends RuntimeException {

  public IncidentNotExpiredException(String message) {
    super(message);
  }
}
