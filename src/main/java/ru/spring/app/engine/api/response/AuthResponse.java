package ru.spring.app.engine.api.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthResponse {
    private boolean result;
    @JsonProperty("user")
    private AuthUserResponse authUserResponse;

    public AuthResponse(boolean result) {
        this.result = result;
    }
}

