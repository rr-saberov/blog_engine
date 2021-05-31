package ru.spring.app.engine.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.spring.app.engine.api.request.ChangePasswordRequest;
import ru.spring.app.engine.api.response.ChangePasswordResponse;
import ru.spring.app.engine.api.request.LoginRequest;
import ru.spring.app.engine.api.response.AuthResponse;
import ru.spring.app.engine.api.response.CaptchaResponse;
import ru.spring.app.engine.api.request.RegistrationRequest;
import ru.spring.app.engine.api.response.RegistrationResponse;
import ru.spring.app.engine.exceptions.RegistrationFailedException;
import ru.spring.app.engine.service.AuthService;
import ru.spring.app.engine.service.CaptchaService;
import ru.spring.app.engine.service.EmailService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.Principal;

@Api
@RestController
@RequestMapping("/api/auth")
public class ApiAuthController {

    private final Logger logger = Logger.getLogger(ApiAuthController.class);
    private final AuthService authService;
    private final CaptchaService captchaService;
    private final EmailService emailService;

    public ApiAuthController(AuthService authService, CaptchaService captchaService, EmailService emailService) {
        this.authService = authService;
        this.captchaService = captchaService;
        this.emailService = emailService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(authService.login(loginRequest));
    }

    @GetMapping("/check")
    @ApiOperation("check")
    public ResponseEntity<AuthResponse> check(Principal principal) {
        if (principal != null) {
            return ResponseEntity.ok(authService.check(principal.getName()));
        } else {
            return ResponseEntity.ok(new AuthResponse(false));
        }
    }

    @GetMapping("/captcha")
    @ApiOperation("method to generate captcha")
    public ResponseEntity<CaptchaResponse> captcha() {
        return ResponseEntity.ok(captchaService.generateCaptcha());
    }

    @GetMapping("/logout")
    public ResponseEntity<Boolean> logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        SecurityContextHolder.clearContext();
        session.invalidate();
        return ResponseEntity.ok(true);
    }

    @PostMapping("/register")
    @ApiOperation("method to registration new user")
    public ResponseEntity<RegistrationResponse> registration(
            @RequestBody RegistrationRequest request) throws RegistrationFailedException {
        return ResponseEntity.ok(authService.registration(request));
    }

    @PostMapping("/restore")
    @ApiOperation("method to restore password")
    public ResponseEntity<Boolean> restore(@RequestParam("email") String email) {
        Boolean result = emailService.restore(email);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/password")
    @ApiOperation("method to change password")
    public ResponseEntity<ChangePasswordResponse> changePassword(
            @RequestBody ChangePasswordRequest request, Principal principal) throws Throwable {
        return ResponseEntity.ok(authService.changePassword(request, principal.getName()));
    }
}
