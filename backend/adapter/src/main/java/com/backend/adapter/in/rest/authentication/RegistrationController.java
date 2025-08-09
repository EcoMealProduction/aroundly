package com.backend.adapter.in.rest.authentication;

import com.backend.adapter.in.dto.request.RegistrationRequestDto;
import com.backend.services.authentication.RegistrationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/public")
public class RegistrationController {

    private final RegistrationService registrationService;

    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @GetMapping("/text")
    public String findAdminName() {
        return "adminovici";
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegistrationRequestDto registrationRequestDto) {
        final String username = registrationRequestDto.username();
        final String email = registrationRequestDto.email();
        final String password = registrationRequestDto.password();

        try {
            String userId = registrationService.registerUser(username, email, password);
            URI location = (userId != null)
                    ? URI.create("/public/users/" + userId)
                    : URI.create("/public/users");
            return ResponseEntity.created(location).body("User created");

        } catch (IllegalStateException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("User creation failed: " + e.getMessage());
        }
    }
}
