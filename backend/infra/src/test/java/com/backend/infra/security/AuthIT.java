package com.backend.infra.security;

import dasniko.testcontainers.keycloak.KeycloakContainer;
import org.junit.jupiter.api.Test;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Testcontainers
class AuthIT {
    private final static int DEFAULT_KEYCLOAK_PORT = 8080;
    private final static String REALM = "test-production";

    @Container
    static KeycloakContainer keycloak = new KeycloakContainer("quay.io/keycloak/keycloak:24.0.1")
            .withRealmImportFile("keycloak/realm-test.json");

    @Test
    void shouldAllowAccessToUserEndpointWithValidToken() throws Exception {
        final URI uri = URI.create("http://" + keycloak.getHost() + ":" +
                keycloak.getMappedPort(DEFAULT_KEYCLOAK_PORT) + "/api/user");
        final String token = obtainToken();

        HttpResponse<String> response = HttpClient.newHttpClient().send(
                HttpRequest.newBuilder()
                        .uri(uri)
                        .header("Authorization", "Bearer" + token)
                        .GET()
                        .build(),
                HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode());
    }

    private String obtainToken() throws IOException, InterruptedException {
        final String username = "mamica";
        final String password = "parolamamei";
        final String url = keycloak.getAuthServerUrl()
                + "/realms/" + REALM + "/protocol/openid-connect/token";
        final String bodyPublisher = "client_id=springboot-app&grant_type=password&username=" + username + "&password=" + password;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString(bodyPublisher))
                .build();

        HttpResponse<String> response = HttpClient.newHttpClient()
                .send(request, HttpResponse.BodyHandlers.ofString());
        String json = response.body();

        return new ObjectMapper().readTree(json).get("access_token").asText();
    }
}
