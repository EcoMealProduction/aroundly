package com.backend.port.inbound.commands.auth;

/**
 * Command object used for user registration.
 *
 * @param email             the email address of the new user
 * @param username          the chosen username
 * @param password          the chosen password
 * @param confirmedPassword the repeated password for confirmation
 */
public record RegisterCommand(
    String email,
    String username,
    String password,
    String confirmedPassword) { }
