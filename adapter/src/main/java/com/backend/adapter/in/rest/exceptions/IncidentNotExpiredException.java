package com.backend.adapter.in.rest.exceptions;

/**
 * Exception thrown when a user attempts to delete
 * an incident that has not yet expired.
 */
public class IncidentNotExpiredException extends RuntimeException {

  public IncidentNotExpiredException(String message) {
    super(message);
  }
}
