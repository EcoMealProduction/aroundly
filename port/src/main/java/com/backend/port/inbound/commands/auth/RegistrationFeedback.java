package com.backend.port.inbound.commands.auth;

/**
 * Represents the feedback returned after a user registration attempt.
 *
 */
public record RegistrationFeedback(String userId) { }
