package ru.spring.app.engine.api.response;

import lombok.Data;
import ru.spring.app.engine.entity.Users;

@Data
public class AuthResponse {

    private boolean result;
    private Users users;
}

