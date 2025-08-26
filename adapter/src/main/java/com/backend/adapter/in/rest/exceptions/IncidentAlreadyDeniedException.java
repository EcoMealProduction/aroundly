package com.backend.adapter.in.rest.exceptions;

/**
 * Exception thrown when a user attempts to deny
 * an incident that has already been denied.
 */
public class IncidentAlreadyDeniedException extends RuntimeException {

  public IncidentAlreadyDeniedException(String message) {
    super(message);
  }
}
