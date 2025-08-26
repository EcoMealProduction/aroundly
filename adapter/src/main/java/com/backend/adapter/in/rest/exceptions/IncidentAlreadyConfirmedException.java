package com.backend.adapter.in.rest.exceptions;

/**
 * Exception thrown when a user attempts to confirm
 * an incident that has already been confirmed.
 */
public class IncidentAlreadyConfirmedException extends RuntimeException {

  public IncidentAlreadyConfirmedException(String message) {
    super(message);
  }
}
