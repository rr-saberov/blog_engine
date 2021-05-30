package ru.spring.app.engine.exceptions;

public class AccessIsDeniedException extends Exception {
    public AccessIsDeniedException(String message) {
        super(message);
    }
}
