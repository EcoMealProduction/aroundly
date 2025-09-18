package com.backend.adapter.inbound.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.backend.adapter.inbound.dto.request.LoginRequestDto;
import com.backend.adapter.inbound.dto.request.RegistrationRequestDto;
import com.backend.adapter.inbound.dto.response.LoginResponseDto;
import com.backend.adapter.inbound.dto.response.RegistrationResponseDto;
import com.backend.port.inbound.commands.auth.LoginCommand;
import com.backend.port.inbound.commands.auth.LoginFeedback;
import com.backend.port.inbound.commands.auth.RegistrationCommand;
import com.backend.port.inbound.commands.auth.RegistrationFeedback;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AuthenticationMapperImpl.class)
class AuthenticationMapperTest {

  @Autowired private AuthenticationMapper mapper;

  private final LoginRequestDto loginRequest = new LoginRequestDto(
      "username",
      "Password1!");

  private final RegistrationRequestDto registrationRequest = new RegistrationRequestDto(
      "username",
      "email@da.com",
      "Password1!");

  private final LoginFeedback loginFeedback = new LoginFeedback(
      "token",
      "tokenType",
      30L,
      "refresh",
      "username",
      "email@da.com");

  private final RegistrationFeedback registrationFeedback = new RegistrationFeedback(
      "Account was created successfully!");

  @Test
  void testToLoginCommand() {
    LoginCommand loginCommand = mapper.toLoginCommand(loginRequest);

    assertEquals(loginCommand.usernameOrEmail(), loginRequest.usernameOrEmail());
    assertEquals(loginCommand.password(), loginRequest.password());
  }

  @Test
  void testToLoginResponse() {
    LoginResponseDto loginResponseDto = mapper.toLoginResponseDto(loginFeedback);

    assertEquals(loginResponseDto.accessToken(), loginFeedback.accessToken());
    assertEquals(loginResponseDto.tokenType(), loginFeedback.tokenType());
    assertEquals(loginResponseDto.expiresIn(), loginFeedback.expiresIn());
    assertEquals(loginResponseDto.refreshToken(), loginFeedback.refreshToken());
    assertEquals(loginResponseDto.username(), loginFeedback.username());
    assertEquals(loginResponseDto.email(), loginFeedback.email());
  }

  @Test
  void testToRegistrationCommand() {
    RegistrationCommand registrationCommand = mapper.toRegisterCommand(registrationRequest);

    assertEquals(registrationCommand.username(), registrationRequest.username());
    assertEquals(registrationCommand.email(), registrationRequest.email());
    assertEquals(registrationCommand.password(), registrationRequest.password());
  }

  @Test
  void testToRegistrationResponse() {
    RegistrationResponseDto registrationResponseDto =
        mapper.toRegistrationResponseDto(registrationFeedback);

    assertEquals(registrationResponseDto.userId(), registrationFeedback.userId());
  }
}
