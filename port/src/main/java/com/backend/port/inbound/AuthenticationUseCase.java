package com.backend.port.inbound;

import com.backend.port.inbound.commands.auth.LoginCommand;
import com.backend.port.inbound.commands.auth.LoginFeedback;
import com.backend.port.inbound.commands.auth.RegisterCommand;
import com.backend.port.inbound.commands.auth.RegistrationFeedback;

/**
 * Defines use cases for handling authentication-related operations,
 * including login and registration.
 */
public interface AuthenticationUseCase {

  /**
   * Authenticates a user based on login credentials.
   *
   * @param loginCommand the command containing login information
   * @return a {@link LoginFeedback} containing authentication details
   */
  LoginFeedback login(LoginCommand loginCommand);

  /**
   * Registers a new user in the system.
   *
   * @param registerCommand the command containing registration information
   * @return a {@link RegistrationFeedback} containing registration result details
   */
  RegistrationFeedback register(RegisterCommand registerCommand);
}
