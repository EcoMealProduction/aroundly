package com.backend.services.authentication;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class RegistrationService {

    private static final Logger log = LoggerFactory.getLogger(RegistrationService.class);
    
    private final KeycloakProperties keycloakProperties;
    private final RestTemplate restTemplate;

    public RegistrationService(KeycloakProperties keycloakProperties) {
        this.keycloakProperties = keycloakProperties;
        this.restTemplate = new RestTemplate();
    }

    public String getAdminAccessToken() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "client_credentials");
        body.add("client_id", keycloakProperties.getClientId());
        body.add("client_secret", keycloakProperties.getClientSecret());

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);
        ResponseEntity<Map> responseEntity = restTemplate.postForEntity(
                keycloakProperties.getTokenUrl(), request, Map.class);

        if (!responseEntity.getStatusCode().is2xxSuccessful() || responseEntity.getBody() == null || responseEntity.getBody().get("access_token") == null) {
            throw new IllegalStateException("Failed to obtain admin access token: " + responseEntity.getStatusCode());
        }
        return responseEntity.getBody().get("access_token").toString();
    }

    public String registerUser(String username, String email, String password) {
        String adminToken = getAdminAccessToken();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(adminToken);

        Map<String, Object> user = new HashMap<>();
        user.put("username", username);
        user.put("email", email);
        user.put("firstName", username);
        user.put("lastName", "User");
        user.put("enabled", true);
        user.put("emailVerified", false);
        
        Map<String, Object> cred = new HashMap<>();
        cred.put("type", "password");
        cred.put("value", password);
        cred.put("temporary", false);
        user.put("credentials", List.of(cred));

        ResponseEntity<String> resp = restTemplate.postForEntity(
                keycloakProperties.getUserCreateUrl(),
                new HttpEntity<>(user, headers),
                String.class
        );

        if (resp.getStatusCode().value() == 201) {
            String location = resp.getHeaders().getFirst(HttpHeaders.LOCATION);
            String userId = (location != null) ? location.substring(location.lastIndexOf('/') + 1) : null;
            log.info("User created successfully with ID: {}", userId);
            return userId;
        } else if (resp.getStatusCode().value() == 409) {
            throw new IllegalStateException("User already exists (409).");
        } else {
            throw new IllegalStateException("User create failed: " + resp.getStatusCode() + " body=" + resp.getBody());
        }
    }
}
