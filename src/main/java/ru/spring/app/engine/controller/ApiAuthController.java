package ru.spring.app.engine.controller;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.spring.app.engine.api.response.CaptchaResponse;
import ru.spring.app.engine.api.response.RegistrationResponse;
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
    public ResponseEntity<Boolean> check() {
        return ResponseEntity.ok(false);
    }

    @GetMapping("/captcha")
    public ResponseEntity<CaptchaResponse> captcha(@RequestParam String code) throws IOException {
        return ResponseEntity.ok(captchaService.generateCaptcha());
    }

    @PostMapping("/register")
    public ResponseEntity<RegistrationResponse> registration(@RequestParam(name = "e_mail", defaultValue = "rdfd@gmail.com") String email,
                                             @RequestParam(defaultValue = "12345fdf") String password,
                                             @RequestParam(defaultValue = "RuslanSab") String name,
                                             @RequestParam(defaultValue = "dfasfsdfsaSDADSA") String captcha,
                                             @RequestParam(name = "captcha_secret", defaultValue = "45rt3") String captchaSecret) {
        return ResponseEntity.ok(authService.newUserRegistration(email, password, name, captcha, captchaSecret));

        
    }
}
