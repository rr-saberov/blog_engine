package ru.spring.app.engine.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.spring.app.engine.api.response.RegistrationResponse;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({PostNotFoundException.class, CaptchaNotFoundException.class})
    public ResponseEntity.HeadersBuilder<?> handlePostNotFoundException() {
        return ResponseEntity.notFound();
    }

    @ExceptionHandler(AccessIsDeniedException.class)
    public ResponseEntity<HttpStatus> handleAccessIsDeniedException() {
        return ResponseEntity.ok(HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(RegistrationFailedException.class)
    public ResponseEntity<RegistrationResponse> handleRegistrationFailedException() {
        return null;
    }
}
