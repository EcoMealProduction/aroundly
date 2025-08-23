package com.backend.port.inbound.commands.auth;

/**
 * Represents the feedback returned after a user registration attempt.
 *
 * @param message a message describing the registration result
 */
public record RegistrationFeedback(String message) {

}
