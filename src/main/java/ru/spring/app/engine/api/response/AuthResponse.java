package ru.spring.app.engine.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AuthResponse {
    private boolean result;

    @JsonProperty("user")
    private AuthUserResponse authUserResponse;
}

