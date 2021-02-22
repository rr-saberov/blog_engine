package ru.spring.app.engine.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.spring.app.engine.service.AuthService;

@RestController
@RequestMapping("/api/auth")
public class ApiAuthController {

    private final AuthService authService;

    public ApiAuthController(AuthService authService) {
        this.authService = authService;
    }


    @GetMapping("/check")
    private boolean check() {
        if (authService.authResponse().isResult()) {
            return true;
        } else {
            return false;
        }
    }

    @PostMapping("/captcha")
    private boolean captcha() {
        return authService.captchaEnter();
    }
}
