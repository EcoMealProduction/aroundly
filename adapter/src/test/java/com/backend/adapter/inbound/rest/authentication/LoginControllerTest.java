package com.backend.adapter.inbound.rest.authentication;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.backend.adapter.in.dto.request.LoginRequestDto;
import com.backend.adapter.in.dto.response.LoginResponseDto;
import com.backend.adapter.in.mapper.AuthenticationMapper;
import com.backend.adapter.in.rest.authentication.LoginController;
import com.backend.port.inbound.AuthenticationUseCase;
import com.backend.port.inbound.commands.auth.LoginCommand;
import com.backend.port.inbound.commands.auth.LoginFeedback;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
class LoginControllerTest {

  private static final String LOGIN_ENDPOINT = "/auth/login";
  private static final String USERNAME = "testuser";
  private static final String EMAIL = "test@example.com";
  private static final String PASSWORD = "password123";
  private static final String ACCESS_TOKEN = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9...";
  private static final String REFRESH_TOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...";

  private final LoginCommand loginCommandWithUsername = new LoginCommand(USERNAME, PASSWORD);
  private final LoginCommand loginCommandWithEmail = new LoginCommand(EMAIL, PASSWORD);
  private final LoginCommand loginCommandWithWrongPassword = new LoginCommand(USERNAME, "wrongpassword");
  private final LoginCommand loginCommandWithEmptyPassword = new LoginCommand(USERNAME, "");
  private final LoginCommand loginCommandWithEmptyUsername = new LoginCommand("", PASSWORD);
  private final LoginCommand loginCommandWithNulls = new LoginCommand(null, null);

  private final LoginFeedback loginFeedback = new LoginFeedback(
      ACCESS_TOKEN,
      "Bearer",
      3600L,
      REFRESH_TOKEN,
      USERNAME,
      EMAIL);

  private final LoginResponseDto loginResponseDto = new LoginResponseDto(
      ACCESS_TOKEN,
      "Bearer",
      3600L,
      REFRESH_TOKEN,
      USERNAME,
      EMAIL);

  private final LoginRequestDto loginRequestWithUsername = new LoginRequestDto(USERNAME, PASSWORD);
  private final LoginRequestDto loginRequestWithEmail = new LoginRequestDto(EMAIL, PASSWORD);
  private final LoginRequestDto loginRequestWithWrongPassword = new LoginRequestDto(
      USERNAME, "wrongpassword");
  private final LoginRequestDto loginRequestWithEmptyPassword = new LoginRequestDto(USERNAME, "");
  private final LoginRequestDto loginRequestWithEmptyUsername = new LoginRequestDto("", PASSWORD);
  private final LoginRequestDto loginRequestWithNulls = new LoginRequestDto(null, null);


  private MockMvc mockMvc;
  private ObjectMapper objectMapper;

  @Mock private AuthenticationUseCase authenticationUseCase;
  @Mock private AuthenticationMapper mapper;
  @InjectMocks private LoginController loginController;

  @BeforeEach
  void setUp() {
    mockMvc = MockMvcBuilders
        .standaloneSetup(loginController)
        .setControllerAdvice()
        .build();
    objectMapper = new ObjectMapper();
  }

  @Test
  @DisplayName("Return 200 OK with valid username")
  void testSuccessfulLoginWithUsername() throws Exception {
    when(mapper.toLoginCommand(loginRequestWithUsername)).thenReturn(loginCommandWithUsername);
    when(authenticationUseCase.login(loginCommandWithUsername)).thenReturn(loginFeedback);
    when(mapper.toLoginResponseDto(loginFeedback)).thenReturn(loginResponseDto);

    mockMvc.perform(post(LOGIN_ENDPOINT)
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(loginRequestWithUsername)))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.accessToken").value(ACCESS_TOKEN))
        .andExpect(jsonPath("$.tokenType").value("Bearer"))
        .andExpect(jsonPath("$.tokenType").value("Bearer"))
        .andExpect(jsonPath("$.expiresIn").value(3600))
        .andExpect(jsonPath("$.refreshToken").value(REFRESH_TOKEN))
        .andExpect(jsonPath("$.username").value(USERNAME))
        .andExpect(jsonPath("$.email").value(EMAIL));

    verify(authenticationUseCase).login(loginCommandWithUsername);
  }

  @Test
  @DisplayName("Return 200 OK with valid email")
  void testDirectMethodCallWithEmail() throws Exception {
    when(mapper.toLoginCommand(loginRequestWithEmail)).thenReturn(loginCommandWithEmail);
    when(authenticationUseCase.login(loginCommandWithEmail)).thenReturn(loginFeedback);
    when(mapper.toLoginResponseDto(loginFeedback)).thenReturn(loginResponseDto);

    mockMvc.perform(post(LOGIN_ENDPOINT)
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(loginRequestWithEmail)))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.accessToken").value(ACCESS_TOKEN))
        .andExpect(jsonPath("$.username").value(USERNAME));

    verify(authenticationUseCase).login(loginCommandWithEmail);
  }

  @Test
  @DisplayName("Handle direct method call successfully")
  void testDirectMethodCall() {
    when(mapper.toLoginCommand(loginRequestWithEmail)).thenReturn(loginCommandWithEmail);
    when(authenticationUseCase.login(loginCommandWithEmail)).thenReturn(loginFeedback);
    when(mapper.toLoginResponseDto(loginFeedback)).thenReturn(loginResponseDto);

    ResponseEntity<LoginResponseDto> response = loginController.login(loginRequestWithEmail);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());
    assertEquals(ACCESS_TOKEN, response.getBody().accessToken());
    assertEquals("Bearer", response.getBody().tokenType());
    assertEquals(3600L, response.getBody().expiresIn());
    assertEquals(REFRESH_TOKEN, response.getBody().refreshToken());
    assertEquals(USERNAME, response.getBody().username());
    assertEquals(EMAIL, response.getBody().email());

    verify(authenticationUseCase).login(loginCommandWithEmail);
  }

  @Test
  @DisplayName("Return 401 UNAUTHORISED for invalid credentials")
  void testInvalidCredentials() throws Exception {
    when(mapper.toLoginCommand(loginRequestWithWrongPassword))
        .thenReturn(loginCommandWithWrongPassword);
    when(authenticationUseCase.login(loginCommandWithWrongPassword))
        .thenThrow(new IllegalArgumentException("Invalid credentials"));

    mockMvc.perform(post(LOGIN_ENDPOINT)
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(loginRequestWithWrongPassword)))
        .andExpect(status().isUnauthorized())
        .andExpect(content().string(""));

    verify(authenticationUseCase).login(loginCommandWithWrongPassword);
  }

  @Test
  @DisplayName("Return direct call 401 UNAUTHORISED for invalid credentials")
  void testDirectCallInvalidCredentials() {
    when(mapper.toLoginCommand(loginRequestWithWrongPassword))
        .thenReturn(loginCommandWithWrongPassword);
    when(authenticationUseCase.login(loginCommandWithWrongPassword))
        .thenThrow(new IllegalArgumentException("Invalid credentials"));

    ResponseEntity<LoginResponseDto> response = loginController.login(loginRequestWithWrongPassword);

    assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    assertNull(response.getBody());

    verify(authenticationUseCase).login(loginCommandWithWrongPassword);
  }

  @Test
  @DisplayName("Return 401 UNAUTHORISED for empty username")
  void testCredentialsWithEmptyUsername() throws Exception {
    when(mapper.toLoginCommand(loginRequestWithEmptyUsername))
        .thenReturn(loginCommandWithEmptyUsername);
    when(authenticationUseCase.login(loginCommandWithEmptyUsername))
        .thenThrow(new IllegalArgumentException("Invalid credentials"));

    mockMvc.perform(post(LOGIN_ENDPOINT)
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(loginRequestWithEmptyUsername)))
        .andExpect(status().isUnauthorized());

    verify(authenticationUseCase).login(loginCommandWithEmptyUsername);
  }

  @Test
  @DisplayName("Return 401 UNAUTHORISED for empty password")
  void testCredentialsWithEmptyPassword() throws Exception{
    when(mapper.toLoginCommand(loginRequestWithEmptyPassword))
        .thenReturn(loginCommandWithEmptyPassword);
    when(authenticationUseCase.login(loginCommandWithEmptyPassword))
        .thenThrow(new IllegalArgumentException("Invalid credentials"));

    mockMvc.perform(post(LOGIN_ENDPOINT)
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(loginRequestWithEmptyPassword)))
        .andExpect(status().isUnauthorized());

    verify(authenticationUseCase).login(loginCommandWithEmptyPassword);
  }

  @Test
  @DisplayName("Return 503 SERVICE_UNAVAILABLE")
  void testServiceUnavailable() throws Exception {
    when(mapper.toLoginCommand(loginRequestWithUsername)).thenReturn(loginCommandWithUsername);
    when(authenticationUseCase.login(loginCommandWithUsername))
        .thenThrow(new IllegalStateException("Authentication service unavailable"));

    mockMvc.perform(post(LOGIN_ENDPOINT)
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(loginRequestWithUsername)))
        .andExpect(status().isServiceUnavailable())
        .andExpect(content().string(""));

    verify(authenticationUseCase).login(loginCommandWithUsername);
  }

  @Test
  @DisplayName("Return direct call 503 SERVICE_UNAVAILABLE")
  void testDirectCallServiceUnavailable() {
    when(mapper.toLoginCommand(loginRequestWithUsername)).thenReturn(loginCommandWithUsername);
    when(authenticationUseCase.login(loginCommandWithUsername))
        .thenThrow(new IllegalStateException("Authentication service unavailable"));

    ResponseEntity<LoginResponseDto> response = loginController.login(loginRequestWithUsername);

    assertEquals(HttpStatus.SERVICE_UNAVAILABLE, response.getStatusCode());
    assertNull(response.getBody());

    verify(authenticationUseCase).login(loginCommandWithUsername);
  }

  @Test
  @DisplayName("Return 500 INTERNAL_SERVER_ERROR")
  void testUnexpectedExceptions() throws Exception {
    when(mapper.toLoginCommand(loginRequestWithUsername)).thenReturn(loginCommandWithUsername);
    when(authenticationUseCase.login(loginCommandWithUsername))
        .thenThrow(new RuntimeException("Unexpected error"));

    mockMvc.perform(post(LOGIN_ENDPOINT)
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(loginRequestWithUsername)))
        .andExpect(status().isInternalServerError())
        .andExpect(content().string(""));

    verify(authenticationUseCase).login(loginCommandWithUsername);
  }

  @Test
  @DisplayName("Return direct call 500 INTERNAL_SERVER_ERROR")
  void testDirectCallUnexpectedExceptions() {
    when(mapper.toLoginCommand(loginRequestWithUsername)).thenReturn(loginCommandWithUsername);
    when(authenticationUseCase.login(loginCommandWithUsername))
        .thenThrow(new RuntimeException("Unexpected error"));

    ResponseEntity<LoginResponseDto> response = loginController.login(loginRequestWithUsername);

    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    assertNull(response.getBody());

    verify(authenticationUseCase).login(loginCommandWithUsername);
  }

  @Test
  @DisplayName("Handle malformed JSON gracefully")
  void testGracefulHandleMalformedJson() throws Exception {
    mockMvc.perform(post(LOGIN_ENDPOINT)
        .contentType(MediaType.APPLICATION_JSON)
        .content("{ invalid json }"))
        .andExpect(status().isBadRequest());

    verifyNoInteractions(authenticationUseCase);
  }

  @Test
  @DisplayName("Handle empty request body")
  void testHandleEmptyRequestBody() throws Exception {
    when(mapper.toLoginCommand(loginRequestWithNulls)).thenReturn(loginCommandWithNulls);
    when(authenticationUseCase.login(loginCommandWithNulls))
        .thenThrow(new IllegalArgumentException("Invalid credentials"));

    mockMvc.perform(post(LOGIN_ENDPOINT)
        .contentType(MediaType.APPLICATION_JSON)
        .content("{}"))
        .andExpect(status().isUnauthorized());

    verify(authenticationUseCase).login(loginCommandWithNulls);
  }

  @Test
  @DisplayName("Handle missing content type")
  void testHandleMissingContentType() throws Exception {
    mockMvc.perform(post(LOGIN_ENDPOINT)
            .content(objectMapper.writeValueAsString(loginRequestWithUsername)))
        .andExpect(status().isUnsupportedMediaType());

    verifyNoInteractions(authenticationUseCase);
  }

  @Test
  @DisplayName("Handle null values in request")
  void testHandleNullValuesInRequest() throws Exception {
    String requestJson = "{\"usernameOrEmail\":null,\"password\":null}";
    when(mapper.toLoginCommand(loginRequestWithNulls)).thenReturn(loginCommandWithNulls);
    when(authenticationUseCase.login(loginCommandWithNulls))
        .thenThrow(new IllegalArgumentException("Invalid credentials"));

    mockMvc.perform(post(LOGIN_ENDPOINT)
        .contentType(MediaType.APPLICATION_JSON)
        .content(requestJson))
        .andExpect(status().isUnauthorized());

    verify(authenticationUseCase).login(loginCommandWithNulls);
  }

  @Test
  @DisplayName("Should only accept POST requests")
  void testAcceptOnlyPostRequests() throws Exception {
    mockMvc.perform(get(LOGIN_ENDPOINT)).andExpect(status().isMethodNotAllowed());

    mockMvc.perform(put(LOGIN_ENDPOINT)
        .contentType(MediaType.APPLICATION_JSON)
        .content("{}"))
        .andExpect(status().isMethodNotAllowed());

    mockMvc.perform(delete(LOGIN_ENDPOINT))
        .andExpect(status().isMethodNotAllowed());

    verifyNoInteractions(authenticationUseCase);
  }

  @Test
  @DisplayName("Return JSON response with correct structure")
  void testReturnJsonResponseWithCorrectStructure() throws Exception {
    when(mapper.toLoginCommand(loginRequestWithUsername)).thenReturn(loginCommandWithUsername);
    when(authenticationUseCase.login(loginCommandWithUsername)).thenReturn(loginFeedback);
    when(mapper.toLoginResponseDto(loginFeedback)).thenReturn(loginResponseDto);

    mockMvc.perform(post(LOGIN_ENDPOINT)
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(loginRequestWithUsername)))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$").isMap())
        .andExpect(jsonPath("$.accessToken").exists())
        .andExpect(jsonPath("$.tokenType").exists())
        .andExpect(jsonPath("$.expiresIn").exists())
        .andExpect(jsonPath("$.refreshToken").exists())
        .andExpect(jsonPath("$.username").exists())
        .andExpect(jsonPath("$.email").exists());
  }
}
