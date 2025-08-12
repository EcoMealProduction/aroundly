package com.backend.adapter.in.rest.authentication;

import com.backend.adapter.in.dto.request.LoginRequestDto;
import com.backend.adapter.in.dto.response.LoginResponseDto;
import com.backend.port.in.LoginUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class LoginController {

    private final LoginUseCase loginUseCase;

    public LoginController(LoginUseCase loginUseCase) {
        this.loginUseCase = loginUseCase;
    }

    @PostMapping("/login")
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
