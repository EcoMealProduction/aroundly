package com.backend.port.inbound.commands.auth;

/**
 * Command object holding configuration properties for connecting to Keycloak.
 *
 * @param baseUrl      the base URL of the Keycloak server
 * @param realm        the Keycloak realm to use
 * @param clientId     the client identifier used for authentication
 * @param clientSecret the client secret used for authentication
 */
public record KeycloakPropertiesCommand(
    String baseUrl,
    String realm,
    String clientId,
    String clientSecret) { }
