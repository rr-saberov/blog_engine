package ru.spring.app.engine.service;

import org.springframework.stereotype.Service;
import ru.spring.app.engine.api.response.AuthResponse;
import ru.spring.app.engine.model.Users;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;

@Service
public class AuthService {

    @PersistenceContext
    private EntityManager entityManager;

    public AuthResponse authResponse() {
        AuthResponse authResponse = new AuthResponse();
        authResponse.setResult(true);
        return authResponse;
    }

    @Transactional
    public void newUserRegistration(@Valid Users users) {
        entityManager.createNativeQuery("INSERT INTO users (id, email, name, password) VALUES (?, ?, ?, ?)")
                .setParameter(1, users.getId())
                .setParameter(2, users.getEmail())
                .setParameter(3, users.getName())
                .setParameter(4, users.getPassword())
                .executeUpdate();
    }
}
