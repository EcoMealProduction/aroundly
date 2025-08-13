package com.backend.adapter.in.rest.authentication;

import com.backend.adapter.in.dto.request.LoginRequestDto;
import com.backend.adapter.in.dto.response.LoginResponseDto;
import com.backend.port.in.LoginUseCase;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("LoginController Tests")
class LoginControllerTest {

    @Mock
    private LoginUseCase loginUseCase;

    @InjectMocks
    private LoginController loginController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    private static final String LOGIN_ENDPOINT = "/auth/login";
    private static final String USERNAME = "testuser";
    private static final String EMAIL = "test@example.com";
    private static final String PASSWORD = "password123";
    private static final String ACCESS_TOKEN = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9...";
    private static final String REFRESH_TOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...";

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(loginController).build();
        objectMapper = new ObjectMapper();
    }

    @Nested
    @DisplayName("Successful Login Tests")
    class SuccessfulLoginTests {

        @Test
        @DisplayName("Should return 200 OK with login response for valid credentials")
        void shouldReturnSuccessfulLoginResponse() throws Exception {

            LoginRequestDto request = new LoginRequestDto(USERNAME, PASSWORD);
            LoginUseCase.LoginResponse mockResponse = createMockLoginResponse();
            when(loginUseCase.authenticateUser(USERNAME, PASSWORD)).thenReturn(mockResponse);


            mockMvc.perform(post(LOGIN_ENDPOINT)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.accessToken").value(ACCESS_TOKEN))
                    .andExpect(jsonPath("$.tokenType").value("Bearer"))
                    .andExpect(jsonPath("$.expiresIn").value(3600))
                    .andExpect(jsonPath("$.refreshToken").value(REFRESH_TOKEN))
                    .andExpect(jsonPath("$.username").value(USERNAME))
                    .andExpect(jsonPath("$.email").value(EMAIL));

            verify(loginUseCase).authenticateUser(USERNAME, PASSWORD);
        }

        @Test
        @DisplayName("Should return 200 OK when authenticating with email")
        void shouldReturnSuccessfulLoginResponseWithEmail() throws Exception {

            LoginRequestDto request = new LoginRequestDto(EMAIL, PASSWORD);
            LoginUseCase.LoginResponse mockResponse = createMockLoginResponse();
            when(loginUseCase.authenticateUser(EMAIL, PASSWORD)).thenReturn(mockResponse);


            mockMvc.perform(post(LOGIN_ENDPOINT)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.accessToken").value(ACCESS_TOKEN))
                    .andExpect(jsonPath("$.username").value(USERNAME));

            verify(loginUseCase).authenticateUser(EMAIL, PASSWORD);
        }

        @Test
        @DisplayName("Should handle direct controller method call successfully")
        void shouldHandleDirectControllerMethodCall() {

            LoginRequestDto request = new LoginRequestDto(USERNAME, PASSWORD);
            LoginUseCase.LoginResponse mockResponse = createMockLoginResponse();
            when(loginUseCase.authenticateUser(USERNAME, PASSWORD)).thenReturn(mockResponse);


            ResponseEntity<LoginResponseDto> response = loginController.login(request);


            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertNotNull(response.getBody());
            assertEquals(ACCESS_TOKEN, response.getBody().accessToken());
            assertEquals("Bearer", response.getBody().tokenType());
            assertEquals(3600L, response.getBody().expiresIn());
            assertEquals(REFRESH_TOKEN, response.getBody().refreshToken());
            assertEquals(USERNAME, response.getBody().username());
            assertEquals(EMAIL, response.getBody().email());

            verify(loginUseCase).authenticateUser(USERNAME, PASSWORD);
        }

        private LoginUseCase.LoginResponse createMockLoginResponse() {
            return new LoginUseCase.LoginResponse(
                    ACCESS_TOKEN,
                    "Bearer",
                    3600L,
                    REFRESH_TOKEN,
                    USERNAME,
                    EMAIL
            );
        }
    }

    @Nested
    @DisplayName("Authentication Failure Tests")
    class AuthenticationFailureTests {

        @Test
        @DisplayName("Should return 401 UNAUTHORIZED for invalid credentials")
        void shouldReturn401ForInvalidCredentials() throws Exception {

            LoginRequestDto request = new LoginRequestDto(USERNAME, "wrongpassword");
            when(loginUseCase.authenticateUser(USERNAME, "wrongpassword"))
                    .thenThrow(new IllegalArgumentException("Invalid credentials"));


            mockMvc.perform(post(LOGIN_ENDPOINT)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isUnauthorized())
                    .andExpect(content().string(""));

            verify(loginUseCase).authenticateUser(USERNAME, "wrongpassword");
        }

        @Test
        @DisplayName("Should return 401 UNAUTHORIZED with direct controller call for invalid credentials")
        void shouldReturn401ForInvalidCredentialsDirectCall() {

            LoginRequestDto request = new LoginRequestDto(USERNAME, "wrongpassword");
            when(loginUseCase.authenticateUser(USERNAME, "wrongpassword"))
                    .thenThrow(new IllegalArgumentException("Invalid credentials"));


            ResponseEntity<LoginResponseDto> response = loginController.login(request);


            assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
            assertNull(response.getBody());

            verify(loginUseCase).authenticateUser(USERNAME, "wrongpassword");
        }

        @Test
        @DisplayName("Should return 401 for empty username")
        void shouldReturn401ForEmptyUsername() throws Exception {

            LoginRequestDto request = new LoginRequestDto("", PASSWORD);
            when(loginUseCase.authenticateUser("", PASSWORD))
                    .thenThrow(new IllegalArgumentException("Invalid credentials"));


            mockMvc.perform(post(LOGIN_ENDPOINT)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isUnauthorized());

            verify(loginUseCase).authenticateUser("", PASSWORD);
        }

        @Test
        @DisplayName("Should return 401 for empty password")
        void shouldReturn401ForEmptyPassword() throws Exception {

            LoginRequestDto request = new LoginRequestDto(USERNAME, "");
            when(loginUseCase.authenticateUser(USERNAME, ""))
                    .thenThrow(new IllegalArgumentException("Invalid credentials"));


            mockMvc.perform(post(LOGIN_ENDPOINT)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isUnauthorized());

            verify(loginUseCase).authenticateUser(USERNAME, "");
        }
    }

    @Nested
    @DisplayName("Service Unavailable Tests")
    class ServiceUnavailableTests {

        @Test
        @DisplayName("Should return 503 SERVICE_UNAVAILABLE when authentication service is down")
        void shouldReturn503ForServiceUnavailable() throws Exception {

            LoginRequestDto request = new LoginRequestDto(USERNAME, PASSWORD);
            when(loginUseCase.authenticateUser(USERNAME, PASSWORD))
                    .thenThrow(new IllegalStateException("Authentication service unavailable"));


            mockMvc.perform(post(LOGIN_ENDPOINT)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isServiceUnavailable())
                    .andExpect(content().string(""));

            verify(loginUseCase).authenticateUser(USERNAME, PASSWORD);
        }

        @Test
        @DisplayName("Should return 503 with direct controller call when service is unavailable")
        void shouldReturn503ForServiceUnavailableDirectCall() {

            LoginRequestDto request = new LoginRequestDto(USERNAME, PASSWORD);
            when(loginUseCase.authenticateUser(USERNAME, PASSWORD))
                    .thenThrow(new IllegalStateException("Authentication service unavailable"));


            ResponseEntity<LoginResponseDto> response = loginController.login(request);


            assertEquals(HttpStatus.SERVICE_UNAVAILABLE, response.getStatusCode());
            assertNull(response.getBody());

            verify(loginUseCase).authenticateUser(USERNAME, PASSWORD);
        }
    }

    @Nested
    @DisplayName("Internal Server Error Tests")
    class InternalServerErrorTests {

        @Test
        @DisplayName("Should return 500 INTERNAL_SERVER_ERROR for unexpected exceptions")
        void shouldReturn500ForUnexpectedExceptions() throws Exception {

            LoginRequestDto request = new LoginRequestDto(USERNAME, PASSWORD);
            when(loginUseCase.authenticateUser(USERNAME, PASSWORD))
                    .thenThrow(new RuntimeException("Unexpected error"));


            mockMvc.perform(post(LOGIN_ENDPOINT)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isInternalServerError())
                    .andExpect(content().string(""));

            verify(loginUseCase).authenticateUser(USERNAME, PASSWORD);
        }

        @Test
        @DisplayName("Should return 500 with direct controller call for unexpected exceptions")
        void shouldReturn500ForUnexpectedExceptionsDirectCall() {

            LoginRequestDto request = new LoginRequestDto(USERNAME, PASSWORD);
            when(loginUseCase.authenticateUser(USERNAME, PASSWORD))
                    .thenThrow(new RuntimeException("Unexpected error"));


            ResponseEntity<LoginResponseDto> response = loginController.login(request);


            assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
            assertNull(response.getBody());

            verify(loginUseCase).authenticateUser(USERNAME, PASSWORD);
        }
    }

    @Nested
    @DisplayName("Request Format Tests")
    class RequestFormatTests {

        @Test
        @DisplayName("Should handle malformed JSON gracefully")
        void shouldHandleMalformedJsonGracefully() throws Exception {

            mockMvc.perform(post(LOGIN_ENDPOINT)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{ invalid json }"))
                    .andExpect(status().isBadRequest());

            verifyNoInteractions(loginUseCase);
        }

        @Test
        @DisplayName("Should handle empty request body")
        void shouldHandleEmptyRequestBody() throws Exception {
            // Given - mock the behavior for null credentials
            when(loginUseCase.authenticateUser(null, null))
                    .thenThrow(new IllegalArgumentException("Invalid credentials"));
            

            mockMvc.perform(post(LOGIN_ENDPOINT)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{}"))
                    .andExpect(status().isUnauthorized());

            verify(loginUseCase).authenticateUser(null, null);
        }

        @Test
        @DisplayName("Should handle missing content type")
        void shouldHandleMissingContentType() throws Exception {

            LoginRequestDto request = new LoginRequestDto(USERNAME, PASSWORD);


            mockMvc.perform(post(LOGIN_ENDPOINT)
                    .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isUnsupportedMediaType());

            verifyNoInteractions(loginUseCase);
        }

        @Test
        @DisplayName("Should handle null values in request")
        void shouldHandleNullValuesInRequest() throws Exception {

            String requestJson = "{\"usernameOrEmail\":null,\"password\":null}";
            when(loginUseCase.authenticateUser(null, null))
                    .thenThrow(new IllegalArgumentException("Invalid credentials"));


            mockMvc.perform(post(LOGIN_ENDPOINT)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(requestJson))
                    .andExpect(status().isUnauthorized());

            verify(loginUseCase).authenticateUser(null, null);
        }
    }

    @Nested
    @DisplayName("HTTP Method Tests")
    class HttpMethodTests {

        @Test
        @DisplayName("Should only accept POST requests")
        void shouldOnlyAcceptPostRequests() throws Exception {
            // When & Then - GET should not be allowed
            mockMvc.perform(get(LOGIN_ENDPOINT))
                    .andExpect(status().isMethodNotAllowed());

            // When & Then - PUT should not be allowed
            mockMvc.perform(put(LOGIN_ENDPOINT)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{}"))
                    .andExpect(status().isMethodNotAllowed());

            // When & Then - DELETE should not be allowed
            mockMvc.perform(delete(LOGIN_ENDPOINT))
                    .andExpect(status().isMethodNotAllowed());

            verifyNoInteractions(loginUseCase);
        }
    }

    @Nested
    @DisplayName("Response Format Tests")
    class ResponseFormatTests {

        @Test
        @DisplayName("Should return JSON response with correct structure")
        void shouldReturnJsonResponseWithCorrectStructure() throws Exception {

            LoginRequestDto request = new LoginRequestDto(USERNAME, PASSWORD);
            LoginUseCase.LoginResponse mockResponse = createMockLoginResponse();
            when(loginUseCase.authenticateUser(USERNAME, PASSWORD)).thenReturn(mockResponse);


            mockMvc.perform(post(LOGIN_ENDPOINT)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
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

        private LoginUseCase.LoginResponse createMockLoginResponse() {
            return new LoginUseCase.LoginResponse(
                    ACCESS_TOKEN,
                    "Bearer",
                    3600L,
                    REFRESH_TOKEN,
                    USERNAME,
                    EMAIL
            );
        }
    }
}