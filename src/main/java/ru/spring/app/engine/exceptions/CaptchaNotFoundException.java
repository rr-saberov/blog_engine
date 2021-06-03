package ru.spring.app.engine.exceptions;

public class CaptchaNotFoundException extends Throwable {
    public CaptchaNotFoundException(String message) {
        super(message);
    }
}
