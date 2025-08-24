package com.backend.services.authentication;

import com.backend.port.inbound.AuthenticationUseCase;
import com.backend.port.inbound.commands.auth.LoginCommand;
import com.backend.port.inbound.commands.auth.LoginFeedback;
import com.backend.port.inbound.commands.auth.RegisterCommand;
import com.backend.port.inbound.commands.auth.RegistrationFeedback;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;


/**
 * Application-layer service for authentication flows against Keycloak.
 *
 * Responsibilities:
 * - Username/password login via Keycloak token endpoint
 * - Admin-backed user registration via Keycloak Admin API
 * - Mapping remote responses into app-facing DTOs
 */
@Service
public class AuthenticationService implements AuthenticationUseCase {

   private final KeycloakProperties keycloakProperties;
   private final RestTemplate restTemplate;

  public AuthenticationService(KeycloakProperties keycloakProperties) {
    this.keycloakProperties = keycloakProperties;
    this.restTemplate = new RestTemplate();
  }

  /**
   * Authenticates a user via Keycloak's token endpoint using the Resource Owner Password Credentials grant.
   *
   * @param loginCommand the login credentials (username/email + password)
   * @return a {@link LoginFeedback} DTO populated from Keycloak's token response
   * @throws IllegalArgumentException when credentials are invalid
   * @throws IllegalStateException    when the auth service is unavailable or returns an unexpected response
   */
  @Override
  public LoginFeedback login(LoginCommand loginCommand) {

    final String usernameOrEmail = loginCommand.usernameOrEmail();
    final String password = loginCommand.password();

    try {
      HttpHeaders headers = new HttpHeaders();
      headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

      MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
      body.add("grant_type", "password");
      body.add("client_id", keycloakProperties.clientId());
      body.add("client_secret", keycloakProperties.clientSecret());
      body.add("username", usernameOrEmail);
      body.add("password", password);

      HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);
      ResponseEntity<Map> responseEntity = restTemplate.postForEntity(
          keycloakProperties.tokenUrl(), request, Map.class);

      if (responseEntity.getStatusCode().is2xxSuccessful() && responseEntity.getBody() != null) {
        Map<String, Object> tokenResponse = responseEntity.getBody();

        return new LoginFeedback(
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

  /**
   * Registers a new user via Keycloak Admin API.
   *
   * @param registerCommand user details for registration (email, username, password)
   * @return a {@link RegistrationFeedback} containing the new user id (in the message field)
   * @throws IllegalStateException on user conflict (409) or unexpected response codes
   */
  @Override
  public RegistrationFeedback register(RegisterCommand registerCommand) {
    final String email = registerCommand.email();
    final String username = registerCommand.username();
    final String password = registerCommand.password();

    String adminToken = getAdminAccessToken();
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.setBearerAuth(adminToken);

    Map<String, Object> user = new HashMap<>();
    user.put("username", username);
    user.put("email", email);
    user.put("enabled", true);

    Map<String, Object> cred = new HashMap<>();
    cred.put("type", "password");
    cred.put("value", password);
    cred.put("temporary", false);
    user.put("credentials", List.of(cred));

    ResponseEntity<String> resp = restTemplate.postForEntity(
        keycloakProperties.userCreateUrl(),
        new HttpEntity<>(user, headers),
        String.class
    );

    if (resp.getStatusCode().value() == 201) {
      String location = resp.getHeaders().getFirst(HttpHeaders.LOCATION);
      String userId = (location != null) ? location.substring(location.lastIndexOf('/') + 1) : null;

      return new RegistrationFeedback(userId);

    } else if (resp.getStatusCode().value() == 409) {
      throw new IllegalStateException("User already exists (409).");
    } else {
      throw new IllegalStateException("User create failed: " + resp.getStatusCode() + " body=" + resp.getBody());
    }
  }

  private String extractEmailFromToken(Map<String, Object> tokenResponse) {
    return null;
  }

  private String getAdminAccessToken() {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

    MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
    body.add("grant_type", "client_credentials");
    body.add("client_id", keycloakProperties.clientId());
    body.add("client_secret", keycloakProperties.clientSecret());

    HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);
    ResponseEntity<Map> responseEntity = restTemplate.postForEntity(
        keycloakProperties.tokenUrl(), request, Map.class);

    if (!responseEntity.getStatusCode().is2xxSuccessful() || responseEntity.getBody() == null || responseEntity.getBody().get("access_token") == null) {
      throw new IllegalStateException("Failed to obtain admin access token: " + responseEntity.getStatusCode());
    }
    return responseEntity.getBody().get("access_token").toString();
  }

}
