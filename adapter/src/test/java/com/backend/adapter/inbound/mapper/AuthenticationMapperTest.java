package com.backend.adapter.inbound.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.backend.adapter.in.dto.request.LoginRequestDto;
import com.backend.adapter.in.dto.response.LoginResponseDto;
import com.backend.adapter.in.mapper.AuthenticationMapper;
import com.backend.adapter.in.mapper.AuthenticationMapperImpl;
import com.backend.port.inbound.commands.auth.LoginCommand;
import com.backend.port.inbound.commands.auth.LoginFeedback;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AuthenticationMapperImpl.class)
class AuthenticationMapperTest {

  @Autowired private AuthenticationMapper mapper;

  @Test
  void testToLoginCommand() {
    LoginRequestDto loginRequestDto = createLoginRequestDto();
    LoginCommand loginCommand = mapper.toLoginCommand(loginRequestDto);

    assertEquals(loginCommand.usernameOrEmail(), loginRequestDto.usernameOrEmail());
    assertEquals(loginCommand.password(), loginRequestDto.password());
  }

  @Test
  void testToLoginResponse() {
    LoginFeedback loginFeedback = createLoginFeedback();
    LoginResponseDto loginResponseDto = mapper.toLoginResponseDto(loginFeedback);

    assertEquals(loginResponseDto.accessToken(), loginFeedback.accessToken());
    assertEquals(loginResponseDto.tokenType(), loginFeedback.tokenType());
    assertEquals(loginResponseDto.expiresIn(), loginFeedback.expiresIn());
    assertEquals(loginResponseDto.refreshToken(), loginFeedback.refreshToken());
    assertEquals(loginResponseDto.username(), loginFeedback.username());
    assertEquals(loginResponseDto.email(), loginFeedback.email());
  }

  private LoginRequestDto createLoginRequestDto() {
    return new LoginRequestDto(
        "username",
        "Password1!");
  }

  private LoginFeedback createLoginFeedback() {
    return new LoginFeedback(
        "token",
        "tokenType",
        30L,
        "refresh",
        "username",
        "email@da.com"
    );
  }
}
