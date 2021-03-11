package ru.spring.app.engine.service;

import com.github.cage.Cage;
import org.springframework.stereotype.Service;
import ru.spring.app.engine.api.response.AuthResponse;
import ru.spring.app.engine.model.Users;
import ru.spring.app.engine.repository.UserRepository;

import javax.validation.Valid;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

@Service
public class AuthService {

    UserRepository userRepository;

    public AuthResponse authResponse() {
        AuthResponse authResponse = new AuthResponse();
        authResponse.setResult(true);
        return authResponse;
    }

    public static void generate(Cage cage, int num, String namePrefix,
                                   String namePostfix, String text) throws IOException {
        for (int fi = 0; fi < num; fi++) {
            OutputStream os = new FileOutputStream(namePrefix + fi
                    + namePostfix, false);
            try {
                cage.draw(text != null ? text : cage.getTokenGenerator().next(), os);
            } finally {
                os.close();
            }
        }
    }

    public void newUserRegistration(@Valid Users users) {
        userRepository.save(users);
    }
}
