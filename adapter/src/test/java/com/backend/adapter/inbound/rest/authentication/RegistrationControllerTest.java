package com.backend.adapter.inbound.rest.authentication;

import com.backend.adapter.inbound.dto.request.RegistrationRequestDto;
import com.backend.adapter.inbound.dto.response.RegistrationResponseDto;
import com.backend.adapter.inbound.mapper.AuthenticationMapper;
import com.backend.port.inbound.commands.auth.RegistrationCommand;
import com.backend.port.inbound.commands.auth.RegistrationFeedback;
import com.backend.services.authentication.AuthenticationService;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(MockitoExtension.class)
public class RegistrationControllerTest {

    private static final String REGISTRATION_ENDPOINT = "/auth/register";
    private static final String USERNAME = "testuser";
    private static final String EMAIL = "test@example.com";
    private static final String PASSWORD = "password123";
    private static final String USER_ID = "abc-123";
    private static final String TEXT_PLAIN_ISO_MEDIA_TYPE = "text/plain;charset=ISO-8859-1";
    private static final String SUCCESSFUL_USER_CREATION_MESSAGE =
        "User was successful registered with ID: abc-123";

    private final RegistrationCommand registrationCommand = new RegistrationCommand(
        EMAIL, USERNAME, PASSWORD);

    private final RegistrationFeedback registrationFeedback = new RegistrationFeedback(USER_ID);

    private final RegistrationRequestDto registrationRequest = new RegistrationRequestDto(
        USERNAME, EMAIL, PASSWORD);

    private final RegistrationResponseDto registrationResponse = new RegistrationResponseDto(USER_ID);

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @Mock private AuthenticationService authenticationService;
    @Mock private AuthenticationMapper mapper;
    @InjectMocks private RegistrationController registrationController;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders
            .standaloneSetup(registrationController)
            .build();
        objectMapper = new ObjectMapper();
    }

    @Test
    @DisplayName("Return 201 CREATED with successful message")
    void testSuccessfulUserRegistration() throws Exception {
        mockDependencies();

        mockMvc.perform(post(REGISTRATION_ENDPOINT)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(registrationRequest)))
            .andExpect(status().isCreated())
            .andExpect(content().contentType(TEXT_PLAIN_ISO_MEDIA_TYPE))
            .andExpect(content().string(SUCCESSFUL_USER_CREATION_MESSAGE));

        verify(authenticationService).register(registrationCommand);
    }

    @Test
    @DisplayName("Handle direct method call successfully")
    void testDirectCallSuccessfulUserRegistration() {
        mockDependencies();

        ResponseEntity<String> response = registrationController.register(registrationRequest);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(SUCCESSFUL_USER_CREATION_MESSAGE, response.getBody());
    }

    private void mockDependencies() {
        when(mapper.toRegisterCommand(registrationRequest)).thenReturn(registrationCommand);
        when(authenticationService.register(registrationCommand)).thenReturn(registrationFeedback);
        when(mapper.toRegistrationResponseDto(registrationFeedback)).thenReturn(registrationResponse);
    }
}
