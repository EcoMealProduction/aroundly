package com.backend.port.inbound.commands.auth;

/**
 * Command object used for user authentication.
 *
 * @param usernameOrEmail the username or email of the user attempting to log in
 * @param password        the user's password
 */
public record LoginCommand(String usernameOrEmail, String password) { }
