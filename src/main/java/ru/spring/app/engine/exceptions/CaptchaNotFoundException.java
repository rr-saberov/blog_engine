package ru.spring.app.engine.exceptions;

public class CaptchaNotFoundException extends Exception {
    public CaptchaNotFoundException(String message) {
        super(message);
    }
}
