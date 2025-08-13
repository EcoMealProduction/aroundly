package com.backend.services.authentication;

import com.backend.port.in.LoginUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("LoginService Tests")
class LoginServiceTest {

    @Mock
    private KeycloakProperties keycloakProperties;
    
    @Mock
    private RestTemplate restTemplate;
    
    private LoginService loginService;

    private static final String USERNAME = "testuser";
    private static final String EMAIL = "test@example.com";
    private static final String PASSWORD = "password123";
    private static final String ACCESS_TOKEN = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9...";
    private static final String REFRESH_TOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...";
    private static final String TOKEN_URL = "http://localhost:7080/realms/glimpse/protocol/openid-connect/token";
    private static final String CLIENT_ID = "aroundly";
    private static final String CLIENT_SECRET = "aroundly-secret";

    @BeforeEach
    void setUp() {
        when(keycloakProperties.getTokenUrl()).thenReturn(TOKEN_URL);
        when(keycloakProperties.getClientId()).thenReturn(CLIENT_ID);
        when(keycloakProperties.getClientSecret()).thenReturn(CLIENT_SECRET);
        
        loginService = new LoginService(keycloakProperties);

        try {
            java.lang.reflect.Field restTemplateField = LoginService.class.getDeclaredField("restTemplate");
            restTemplateField.setAccessible(true);
            restTemplateField.set(loginService, restTemplate);
        } catch (Exception e) {
            throw new RuntimeException("Failed to inject mock RestTemplate", e);
        }
    }

    @Nested
    @DisplayName("Successful Authentication Tests")
    class SuccessfulAuthenticationTests {

        @Test
        @DisplayName("Should authenticate user with valid username and password")
        void shouldAuthenticateUserWithValidCredentials() {
            Map<String, Object> tokenResponse = createSuccessfulTokenResponse();
            ResponseEntity<Map<String, Object>> responseEntity = new ResponseEntity<>(tokenResponse, HttpStatus.OK);
            
            when(restTemplate.postForEntity(eq(TOKEN_URL), any(), any(Class.class)))
                    .thenReturn(responseEntity);

            LoginUseCase.LoginResponse result = loginService.authenticateUser(USERNAME, PASSWORD);

            assertNotNull(result);
            assertEquals(ACCESS_TOKEN, result.accessToken());
            assertEquals("Bearer", result.tokenType());
            assertEquals(3600L, result.expiresIn());
            assertEquals(REFRESH_TOKEN, result.refreshToken());
            assertEquals(USERNAME, result.username());
            assertNull(result.email()); // Email extraction not implemented yet
            
            verify(restTemplate).postForEntity(eq(TOKEN_URL), any(), any(Class.class));
        }

        @Test
        @DisplayName("Should authenticate user with valid email and password")
        void shouldAuthenticateUserWithValidEmail() {

            Map<String, Object> tokenResponse = createSuccessfulTokenResponse();
            ResponseEntity<Map<String, Object>> responseEntity = new ResponseEntity<>(tokenResponse, HttpStatus.OK);
            
            when(restTemplate.postForEntity(eq(TOKEN_URL), any(), any(Class.class)))
                    .thenReturn(responseEntity);

            LoginUseCase.LoginResponse result = loginService.authenticateUser(EMAIL, PASSWORD);

            assertNotNull(result);
            assertEquals(ACCESS_TOKEN, result.accessToken());
            assertEquals("Bearer", result.tokenType());
            assertEquals(3600L, result.expiresIn());
            assertEquals(REFRESH_TOKEN, result.refreshToken());
            assertEquals(EMAIL, result.username());
            assertNull(result.email()); // Email extraction not implemented yet
        }

        private Map<String, Object> createSuccessfulTokenResponse() {
            Map<String, Object> tokenResponse = new HashMap<>();
            tokenResponse.put("access_token", ACCESS_TOKEN);
            tokenResponse.put("token_type", "Bearer");
            tokenResponse.put("expires_in", 3600);
            tokenResponse.put("refresh_token", REFRESH_TOKEN);
            return tokenResponse;
        }
    }

    @Nested
    @DisplayName("Authentication Failure Tests")
    class AuthenticationFailureTests {

        @Test
        @DisplayName("Should throw IllegalArgumentException for invalid credentials")
        void shouldThrowExceptionForInvalidCredentials() {

            ResponseEntity<Map<String, Object>> responseEntity = new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            when(restTemplate.postForEntity(eq(TOKEN_URL), any(), any(Class.class)))
                    .thenReturn(responseEntity);


            IllegalArgumentException exception = assertThrows(
                    IllegalArgumentException.class,
                    () -> loginService.authenticateUser(USERNAME, "wrongpassword")
            );
            
            assertEquals("Invalid credentials", exception.getMessage());
        }

        @Test
        @DisplayName("Should throw IllegalArgumentException when response body is null")
        void shouldThrowExceptionWhenResponseBodyIsNull() {

            ResponseEntity<Map<String, Object>> responseEntity = new ResponseEntity<>(null, HttpStatus.OK);
            when(restTemplate.postForEntity(eq(TOKEN_URL), any(), any(Class.class)))
                    .thenReturn(responseEntity);


            IllegalArgumentException exception = assertThrows(
                    IllegalArgumentException.class,
                    () -> loginService.authenticateUser(USERNAME, PASSWORD)
            );
            
            assertEquals("Invalid credentials", exception.getMessage());
        }

        @Test
        @DisplayName("Should throw IllegalArgumentException for 401 RestClientException")
        void shouldThrowExceptionFor401RestClientException() {

            RestClientException restClientException = new RestClientException("401 Unauthorized: invalid_grant");
            when(restTemplate.postForEntity(eq(TOKEN_URL), any(), any(Class.class)))
                    .thenThrow(restClientException);


            IllegalArgumentException exception = assertThrows(
                    IllegalArgumentException.class,
                    () -> loginService.authenticateUser(USERNAME, PASSWORD)
            );
            
            assertEquals("Invalid credentials", exception.getMessage());
        }

        @Test
        @DisplayName("Should throw IllegalArgumentException for invalid_grant RestClientException")
        void shouldThrowExceptionForInvalidGrantRestClientException() {

            RestClientException restClientException = new RestClientException("Bad Request: invalid_grant");
            when(restTemplate.postForEntity(eq(TOKEN_URL), any(), any(Class.class)))
                    .thenThrow(restClientException);


            IllegalArgumentException exception = assertThrows(
                    IllegalArgumentException.class,
                    () -> loginService.authenticateUser(USERNAME, PASSWORD)
            );
            
            assertEquals("Invalid credentials", exception.getMessage());
        }
    }

    @Nested
    @DisplayName("Service Unavailable Tests")
    class ServiceUnavailableTests {

        @Test
        @DisplayName("Should throw IllegalStateException for service unavailable")
        void shouldThrowExceptionForServiceUnavailable() {

            RestClientException restClientException = new RestClientException("Connection refused");
            when(restTemplate.postForEntity(eq(TOKEN_URL), any(), any(Class.class)))
                    .thenThrow(restClientException);


            IllegalStateException exception = assertThrows(
                    IllegalStateException.class,
                    () -> loginService.authenticateUser(USERNAME, PASSWORD)
            );
            
            assertTrue(exception.getMessage().contains("Authentication service unavailable"));
            assertTrue(exception.getMessage().contains("Connection refused"));
        }

        @Test
        @DisplayName("Should throw IllegalStateException for network timeout")
        void shouldThrowExceptionForNetworkTimeout() {

            RestClientException restClientException = new RestClientException("Read timed out");
            when(restTemplate.postForEntity(eq(TOKEN_URL), any(), any(Class.class)))
                    .thenThrow(restClientException);


            IllegalStateException exception = assertThrows(
                    IllegalStateException.class,
                    () -> loginService.authenticateUser(USERNAME, PASSWORD)
            );
            
            assertTrue(exception.getMessage().contains("Authentication service unavailable"));
            assertTrue(exception.getMessage().contains("Read timed out"));
        }
    }

    @Nested
    @DisplayName("Edge Case Tests")
    class EdgeCaseTests {

        @Test
        @DisplayName("Should handle null values in token response gracefully")
        void shouldHandleNullValuesInTokenResponse() {

            Map<String, Object> tokenResponse = new HashMap<>();
            tokenResponse.put("access_token", ACCESS_TOKEN);
            tokenResponse.put("token_type", "Bearer");
            tokenResponse.put("expires_in", 3600);
            tokenResponse.put("refresh_token", null); // Null refresh token
            
            ResponseEntity<Map<String, Object>> responseEntity = new ResponseEntity<>(tokenResponse, HttpStatus.OK);
            when(restTemplate.postForEntity(eq(TOKEN_URL), any(), any(Class.class)))
                    .thenReturn(responseEntity);

            LoginUseCase.LoginResponse result = loginService.authenticateUser(USERNAME, PASSWORD);

            assertNotNull(result);
            assertEquals(ACCESS_TOKEN, result.accessToken());
            assertEquals("Bearer", result.tokenType());
            assertEquals(3600L, result.expiresIn());
            assertNull(result.refreshToken());
            assertEquals(USERNAME, result.username());
        }

        @Test
        @DisplayName("Should handle empty username")
        void shouldHandleEmptyUsername() {

            Map<String, Object> tokenResponse = createSuccessfulTokenResponse();
            ResponseEntity<Map<String, Object>> responseEntity = new ResponseEntity<>(tokenResponse, HttpStatus.OK);
            
            when(restTemplate.postForEntity(eq(TOKEN_URL), any(), any(Class.class)))
                    .thenReturn(responseEntity);

            LoginUseCase.LoginResponse result = loginService.authenticateUser("", PASSWORD);

            assertNotNull(result);
            assertEquals("", result.username());
        }

        private Map<String, Object> createSuccessfulTokenResponse() {
            Map<String, Object> tokenResponse = new HashMap<>();
            tokenResponse.put("access_token", ACCESS_TOKEN);
            tokenResponse.put("token_type", "Bearer");
            tokenResponse.put("expires_in", 3600);
            tokenResponse.put("refresh_token", REFRESH_TOKEN);
            return tokenResponse;
        }
    }

    @Nested
    @DisplayName("Email Extraction Tests")
    class EmailExtractionTests {

        @Test
        @DisplayName("Should return null for email extraction (not implemented)")
        void shouldReturnNullForEmailExtraction() {

            Map<String, Object> tokenResponse = createSuccessfulTokenResponse();
            ResponseEntity<Map<String, Object>> responseEntity = new ResponseEntity<>(tokenResponse, HttpStatus.OK);
            
            when(restTemplate.postForEntity(eq(TOKEN_URL), any(), any(Class.class)))
                    .thenReturn(responseEntity);

            LoginUseCase.LoginResponse result = loginService.authenticateUser(USERNAME, PASSWORD);

            assertNull(result.email());
        }

        private Map<String, Object> createSuccessfulTokenResponse() {
            Map<String, Object> tokenResponse = new HashMap<>();
            tokenResponse.put("access_token", ACCESS_TOKEN);
            tokenResponse.put("token_type", "Bearer");
            tokenResponse.put("expires_in", 3600);
            tokenResponse.put("refresh_token", REFRESH_TOKEN);
            return tokenResponse;
        }
    }
}