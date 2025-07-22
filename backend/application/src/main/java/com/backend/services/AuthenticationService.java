package com.backend.services;

import com.backend.port.in.AuthenticationUseCase;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService implements AuthenticationUseCase {

    @Override
    public String login(String email, String password) {
        return "";
    }

    @Override
    public String signUp(String username, String email, String password) {
        return "";
    }

    @Override
    public String loginOAuth2(String provider, String accessToken) {
        return "";
    }

    @Override
    public void logout(String token) {

    }

    @Override
    public void requestPasswordReset(String email) {

    }

    @Override
    public void resetPassword(String token, String newPassword) {

    }
}
