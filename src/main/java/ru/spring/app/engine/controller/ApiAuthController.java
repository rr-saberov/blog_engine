package ru.spring.app.engine.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import ru.spring.app.engine.api.request.LoginRequest;
import ru.spring.app.engine.api.response.AuthResponse;
import ru.spring.app.engine.api.response.AuthUserResponse;
import ru.spring.app.engine.api.response.CaptchaResponse;
import ru.spring.app.engine.api.response.RegistrationResponse;
import ru.spring.app.engine.entity.Users;
import ru.spring.app.engine.repository.UserRepository;
import ru.spring.app.engine.service.AuthService;
import ru.spring.app.engine.service.CaptchaService;

import java.security.Principal;

@Api
@RestController
@RequestMapping("/api/auth")
public class ApiAuthController {

    private final Logger logger = Logger.getLogger(ApiAuthController.class);
    private final AuthService authService;
    private final CaptchaService captchaService;

    @Autowired
    public ApiAuthController(AuthService authService, CaptchaService captchaService) {
        this.authService = authService;
        this.captchaService = captchaService;
    }

    @GetMapping("/check")
    @ApiOperation("check")
    public ResponseEntity<AuthResponse> check(Principal principal) {
        if (principal == null) {
            return ResponseEntity.ok(new AuthResponse());
        }
        return ResponseEntity.ok(authService.check(principal.getName()));
    }

    @GetMapping("/captcha")
    @ApiOperation("method to generate captcha")
    public ResponseEntity<CaptchaResponse> captcha() {
        return ResponseEntity.ok(captchaService.generateCaptcha());
    }

    @GetMapping("/logout")
    public ResponseEntity<Boolean> logout() {
        return ResponseEntity.ok(true);
    }

    @PostMapping("/register")
    @ApiOperation("method to registration new user")
    public ResponseEntity<RegistrationResponse> registration(@RequestParam(name = "e_mail", defaultValue = "rdfd@gmail.com") String email,
                                             @RequestParam(defaultValue = "12345fdf") String password,
                                             @RequestParam(defaultValue = "RuslanSab") String name,
                                             @RequestParam(defaultValue = "dfasfSDADSA") String captcha,
                                             @RequestParam(name = "captcha_secret", defaultValue = "45rt3") String captchaSecret) {
        return ResponseEntity.ok(authService.newUserRegistration(email, password, name, captcha, captchaSecret));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(authService.login(loginRequest));
    }
}
