package ru.spring.app.engine.controller;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.spring.app.engine.entity.Users;
import ru.spring.app.engine.service.AuthService;
import ru.spring.app.engine.service.CaptchaService;

import java.io.IOException;

@Api
@RestController
@RequestMapping("/api/auth")
public class ApiAuthController {

    private final AuthService authService;
    private final CaptchaService captchaService;

    @Autowired
    public ApiAuthController(AuthService authService, CaptchaService captchaService) {
        this.authService = authService;
        this.captchaService = captchaService;
    }

    @GetMapping("/check")
    private boolean check(@RequestBody Users users) {
        return authService.authResponse(users).isResult();
    }

    @PostMapping("/captcha")
    private boolean captcha(@RequestParam String code) throws IOException {
        return captchaService.validCaptcha(code);
    }

    @PostMapping("/register")
    private void regUser(@RequestBody Users users) {
        authService.newUserRegistration(users);
    }
}
