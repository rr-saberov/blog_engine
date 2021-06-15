package ru.spring.app.engine.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class AccessIsDeniedException extends Throwable {
    public AccessIsDeniedException(String message) {
        super(message);
    }
}
