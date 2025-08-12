package com.backend.adapter.in.rest.authentication;

import com.backend.adapter.in.dto.request.LoginRequestDto;
import com.backend.adapter.in.dto.response.LoginResponseDto;
import com.backend.port.in.LoginUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for user authentication operations.
 * 
 * This controller provides public endpoints for user login functionality,
 * handling authentication requests and returning appropriate responses
 * based on the authentication outcome.
 */
@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication", description = "User login and authentication endpoints")
public class LoginController {

    private final LoginUseCase loginUseCase;

    public LoginController(LoginUseCase loginUseCase) {
        this.loginUseCase = loginUseCase;
    }

    @PostMapping("/login")
    @Operation(
            summary = "User login",
            description = "Authenticates a user using username/email and password, returning JWT tokens and user information"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", 
                    description = "Authentication successful",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = LoginResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "401", 
                    description = "Invalid credentials",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "503", 
                    description = "Authentication service unavailable",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "500", 
                    description = "Internal server error",
                    content = @Content
            )
    })
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequestDto) {
        try {
            LoginUseCase.LoginResponse loginResponse = loginUseCase.authenticateUser(
                    loginRequestDto.usernameOrEmail(),
                    loginRequestDto.password()
            );

            LoginResponseDto responseDto = new LoginResponseDto(
                    loginResponse.accessToken(),
                    loginResponse.tokenType(),
                    loginResponse.expiresIn(),
                    loginResponse.refreshToken(),
                    loginResponse.username(),
                    loginResponse.email()
            );

            return ResponseEntity.ok(responseDto);

        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (IllegalStateException ex) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
