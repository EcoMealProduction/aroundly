package com.backend.adapter.in.rest.exceptions;

/**
 * Exception thrown when an incident already exists
 * and a duplicate creation attempt is made.
 */
public class DuplicateIncidentException extends RuntimeException {
  public DuplicateIncidentException(String message) {
    super(message);
  }
}
