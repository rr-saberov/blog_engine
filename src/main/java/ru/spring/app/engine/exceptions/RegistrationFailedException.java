package ru.spring.app.engine.exceptions;

public class RegistrationFailedException extends Exception {
    public RegistrationFailedException(String message) {
        super(message);
    }
}
