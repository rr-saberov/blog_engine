package ru.spring.app.engine.exceptions;

public class AccessIsDeniedException extends Throwable {
    public AccessIsDeniedException(String message) {
        super(message);
    }
}
