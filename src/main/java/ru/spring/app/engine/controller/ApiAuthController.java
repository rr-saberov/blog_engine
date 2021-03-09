package ru.spring.app.engine.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.spring.app.engine.model.Users;
import ru.spring.app.engine.service.AuthService;

import java.io.IOException;

@RestController
@RequestMapping("/api/auth")
public class ApiAuthController {

    private final AuthService authService;

    public ApiAuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/check")
    private boolean check() {
        return authService.authResponse().isResult();
    }

 //   @PostMapping("/captcha")
 //   private boolean captcha() throws IOException {
   //     return authService.captchaEnter();
//    }

    @PostMapping("/register")
    private void regUser(Users users) {
        authService.newUserRegistration(users);
    }
}
