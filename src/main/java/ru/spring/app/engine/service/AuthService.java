package ru.spring.app.engine.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.spring.app.engine.api.request.ChangePasswordRequest;
import ru.spring.app.engine.api.request.LoginRequest;
import ru.spring.app.engine.api.response.AuthResponse;
import ru.spring.app.engine.api.response.AuthUserResponse;
import ru.spring.app.engine.api.request.RegistrationRequest;
import ru.spring.app.engine.api.response.ChangePasswordResponse;
import ru.spring.app.engine.api.response.RegistrationResponse;
import ru.spring.app.engine.entity.Captcha;
import ru.spring.app.engine.exceptions.CaptchaNotFoundException;
import ru.spring.app.engine.exceptions.RegistrationFailedException;
import ru.spring.app.engine.repository.CaptchaRepository;
import ru.spring.app.engine.repository.UserRepository;

import java.util.ArrayList;
import java.util.Date;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final CaptchaRepository captchaRepository;

    public AuthService(UserRepository userRepository, AuthenticationManager authenticationManager, CaptchaRepository captchaRepository) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.captchaRepository = captchaRepository;
    }

    public AuthResponse login(LoginRequest loginRequest) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(auth);
        User user = (User) auth.getPrincipal();
        return convertToResponse(user.getUsername());
    }

    public AuthResponse check(String name) {
        return convertToResponse(name);
    }

    public RegistrationResponse registration(RegistrationRequest request) throws RegistrationFailedException {
        if (userRepository.findByEmail(request.getEmail()).isEmpty()) {
            ru.spring.app.engine.entity.User user = new ru.spring.app.engine.entity.User();
            user.setEmail(request.getEmail());
            user.setIsModerator(-1);
            user.setPassword(request.getPassword());
            user.setName(request.getName());
            user.setRegTime(new Date(System.currentTimeMillis()));
            userRepository.save(user);
            return new RegistrationResponse(true, new ArrayList<>());
        } else {
            throw new RegistrationFailedException("Registration failed check correctness of input");
        }
    }

    private AuthResponse convertToResponse(String email) {
        ru.spring.app.engine.entity.User currentUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(email));
        AuthUserResponse userResponse = new AuthUserResponse();
        userResponse.setEmail(currentUser.getEmail());
        userResponse.setName(currentUser.getName());
        userResponse.setModeration(currentUser.getIsModerator() == 1);
        userResponse.setId(currentUser.getId());
        AuthResponse authResponse = new AuthResponse();
        authResponse.setResult(true);
        authResponse.setAuthUserResponse(userResponse);
        return authResponse;
    }

    public ChangePasswordResponse changePassword(ChangePasswordRequest request, String email) throws Throwable {
        ChangePasswordResponse response = new ChangePasswordResponse();
        Captcha captcha = captchaRepository
                .findBySecretCode(request.getCaptchaSecret()).orElseThrow(() ->
                        new CaptchaNotFoundException(request.getCaptchaSecret()));
        if (captcha.getSecretCode().equals(request.getCaptchaSecret()) &&
                captcha.getCode().equals(request.getCaptchaSecret()) &&
                request.getCode().equals(userRepository.findByEmail(email))) {
            response.setResult(true);
        } else {
            response.setResult(false);
            response.setErrors(new ArrayList<>());
        }
        return response;
    }
}
