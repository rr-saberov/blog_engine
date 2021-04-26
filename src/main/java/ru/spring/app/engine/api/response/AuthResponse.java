package ru.spring.app.engine.api.response;

import lombok.Data;

@Data
public class AuthResponse {
    private boolean result;
    private AuthUserResponse authUserResponse;
}

