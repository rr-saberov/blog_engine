package ru.spring.app.engine.service;

import org.springframework.stereotype.Service;
import ru.spring.app.engine.api.response.AuthResponse;

@Service
public class AuthService {

    public AuthResponse authResponse() {
        AuthResponse authResponse = new AuthResponse();
        authResponse.setResult(true);
        return authResponse;
    }

    public boolean captchaEnter() {
        return false;
    }

}
