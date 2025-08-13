package com.backend.services.authentication;

import com.backend.port.in.LoginUseCase;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * Service implementation for user authentication using Keycloak OAuth2.
 * 
 * This service handles user authentication by integrating with Keycloak's
 * token endpoint using the Resource Owner Password Credentials (ROPC) flow.
 * It manages the communication with Keycloak and transforms responses into
 * application-specific data structures.
 */
@Service
public class LoginService implements LoginUseCase {

    private final KeycloakProperties keycloakProperties;
    private final RestTemplate restTemplate;

    public LoginService(KeycloakProperties keycloakProperties) {
        this.keycloakProperties = keycloakProperties;
        this.restTemplate = new RestTemplate();
    }

    @Override
    public LoginResponse authenticateUser(String usernameOrEmail, String password) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
            body.add("grant_type", "password");
            body.add("client_id", keycloakProperties.getClientId());
            body.add("client_secret", keycloakProperties.getClientSecret());
            body.add("username", usernameOrEmail);
            body.add("password", password);

            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);
            ResponseEntity<Map> responseEntity = restTemplate.postForEntity(
                    keycloakProperties.getTokenUrl(), request, Map.class);

            if (responseEntity.getStatusCode().is2xxSuccessful() && responseEntity.getBody() != null) {
                Map<String, Object> tokenResponse = responseEntity.getBody();
                
                return new LoginResponse(
                        (String) tokenResponse.get("access_token"),
                        (String) tokenResponse.get("token_type"),
                        Long.parseLong(tokenResponse.get("expires_in").toString()),
                        (String) tokenResponse.get("refresh_token"),
                        usernameOrEmail,
                        extractEmailFromToken(tokenResponse)
                );
            } else {
                throw new IllegalArgumentException("Invalid credentials");
            }

        } catch (RestClientException ex) {
            if (ex.getMessage() != null && (ex.getMessage().contains("401") || ex.getMessage().contains("invalid_grant"))) {
                throw new IllegalArgumentException("Invalid credentials");
            }
            throw new IllegalStateException("Authentication service unavailable: " + ex.getMessage());
        }
    }

    private String extractEmailFromToken(Map<String, Object> tokenResponse) {
        return null;
    }
}
