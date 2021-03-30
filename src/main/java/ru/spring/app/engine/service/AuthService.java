package ru.spring.app.engine.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.spring.app.engine.api.response.AuthResponse;
import ru.spring.app.engine.entity.Users;
import ru.spring.app.engine.repository.UserRepository;

import javax.validation.Valid;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AuthService {

    private UserRepository userRepository;

    @Autowired
    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public AuthResponse authResponse(Users user) {
        AuthResponse authResponse = new AuthResponse();

        Map<String, String> usersMap = userRepository.findAll().stream()
                .collect(Collectors.toMap(Users::getName, Users::getPassword));

        authResponse.setResult
                (usersMap.containsKey(user.getName()) && usersMap.containsValue(user.getPassword()));

        return authResponse;
    }

    public void newUserRegistration(@Valid Users users) {
        userRepository.save(users);
    }
}
