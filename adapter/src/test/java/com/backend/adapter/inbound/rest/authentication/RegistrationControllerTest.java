package com.backend.adapter.inbound.rest.authentication;

import com.backend.adapter.in.dto.request.RegistrationRequestDto;
import com.backend.adapter.in.rest.authentication.RegistrationController;
import com.backend.services.authentication.RegistrationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith(MockitoExtension.class)
public class RegistrationControllerTest {

    @Mock private RegistrationService registrationService;
    @InjectMocks private RegistrationController registrationController;

    @Test
    void testSuccessfulUserRegistration() throws Exception {
        RegistrationRequestDto registrationRequestDto = new RegistrationRequestDto(
                "Bob", "bob@da.com", "Password1!");
        ResponseEntity<String> response = registrationController.register(registrationRequestDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("User created", response.getBody());
    }
}
