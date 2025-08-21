package com.backend.adapter.in.rest.authentication;

import com.backend.adapter.in.dto.request.RegistrationRequestDto;
import com.backend.services.authentication.RegistrationService;
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

    private final RegistrationService registrationService;

    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
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
        final String username = registrationRequestDto.username();
        final String email = registrationRequestDto.email();
        final String password = registrationRequestDto.password();

        try {
            String userId = registrationService.registerUser(username, email, password);
            URI location = (userId != null)
                    ? URI.create("/auth/users/" + userId)
                    : URI.create("/auth/users");
            return ResponseEntity.created(location).body("User created");

        } catch (IllegalStateException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("User creation failed: " + e.getMessage());
        }
    }
}
