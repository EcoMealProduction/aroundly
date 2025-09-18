package com.backend.adapter.inbound.rest.exception.incident;

/**
 * Exception thrown when a user attempts to confirm
 * an incident that has already been confirmed.
 */
public class IncidentAlreadyConfirmedException extends RuntimeException {

  public IncidentAlreadyConfirmedException(String message) {
    super(message);
  }
}
