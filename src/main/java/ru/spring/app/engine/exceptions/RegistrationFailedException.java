package ru.spring.app.engine.exceptions;

public class RegistrationFailedException extends Throwable {
    public RegistrationFailedException(String message) {
        super(message);
    }
}
