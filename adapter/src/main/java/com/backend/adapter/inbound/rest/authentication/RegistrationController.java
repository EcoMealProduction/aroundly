package com.backend.adapter.inbound.rest.authentication;

import com.backend.adapter.inbound.dto.request.RegistrationRequestDto;
import com.backend.adapter.inbound.dto.response.RegistrationResponseDto;
import com.backend.adapter.inbound.mapper.AuthenticationMapper;
import com.backend.port.inbound.AuthenticationUseCase;
import com.backend.port.inbound.commands.auth.RegistrationCommand;
import com.backend.port.inbound.commands.auth.RegistrationFeedback;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication", description = "User registration and authentication endpoints")
public class RegistrationController {

    private final AuthenticationUseCase authenticationUseCase;
    private final AuthenticationMapper mapper;

    public RegistrationController(
        AuthenticationUseCase authenticationUseCase,
        AuthenticationMapper mapper) {

        this.authenticationUseCase = authenticationUseCase;
        this.mapper = mapper;
    }

    @GetMapping("/text")
    @Operation(
            summary = "Get admin name",
            description = "Public endpoint to get admin name - test endpoint"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved admin name")
    })
    public String findAdminName() {
        return "adminovici";
    }

    @PostMapping("/register")
    @Operation(
            summary = "Register new user",
            description = "Creates a new user account in the system"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User successfully created"),
            @ApiResponse(responseCode = "400", description = "Invalid password format - must be minimum 8 characters with 1 digit, 1 uppercase, 1 special character"),
            @ApiResponse(responseCode = "409", description = "User already exists (username or email)"),
            @ApiResponse(responseCode = "500", description = "Internal server error during user creation")
    })
    public ResponseEntity<String> register(@RequestBody RegistrationRequestDto registrationRequestDto) {
        final String userCreationEndpoint = "/auth/users";

        try {
            RegistrationCommand registrationCommand = mapper.toRegisterCommand(registrationRequestDto);
            RegistrationFeedback registrationFeedback = authenticationUseCase.register(registrationCommand);
            RegistrationResponseDto registrationResponseDto = mapper.toRegistrationResponseDto(registrationFeedback);
            URI location = (registrationResponseDto.userId() != null)
                    ? URI.create(userCreationEndpoint + "/" + registrationResponseDto.userId())
                    : URI.create(userCreationEndpoint);

            return ResponseEntity.created(location).body(registrationResponseDto
                .successfulRegistrationMessage());

        } catch (IllegalStateException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("User creation failed: " + e.getMessage());
        }
    }
}
