package ru.spring.app.engine.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.spring.app.engine.api.request.LoginRequest;
import ru.spring.app.engine.api.response.AuthResponse;
import ru.spring.app.engine.api.response.AuthUserResponse;
import ru.spring.app.engine.api.response.RegistrationResponse;
import ru.spring.app.engine.entity.Users;
import ru.spring.app.engine.repository.UserRepository;

import java.util.Date;

@Service
public class AuthService {

    private UserRepository userRepository;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthService(UserRepository userRepository, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
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

    public RegistrationResponse newUserRegistration(String email, String password, String name,
                                    String captcha, String captchaSecret) {
        Users user = new Users();
        user.setEmail(email);
        user.setIsModerator(-1);
        user.setPassword(password);
        user.setName(name);
        user.setRegTime(new Date(System.currentTimeMillis()));
        userRepository.save(user);
        return new RegistrationResponse(email, password, name, captcha, captchaSecret);
    }

    private AuthResponse convertToResponse(String email) {
        Users currentUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(email));
        AuthUserResponse userResponse = new AuthUserResponse();
        userResponse.setEmail(currentUser.getEmail());
        userResponse.setName(currentUser.getName());
        userResponse.setModeration(currentUser.getIsModerator() == 1);
        userResponse.setId(currentUser.getId());
        AuthResponse authResponse = new AuthResponse();
        authResponse.setResult(true);
        authResponse.setAuthUserResponse(userResponse);
        return authResponse ;
    }

}
