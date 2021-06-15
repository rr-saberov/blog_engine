package ru.spring.app.engine.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.spring.app.engine.api.request.EditProfileRequest;
import ru.spring.app.engine.api.response.EditProfileResponse;
import ru.spring.app.engine.api.response.errors.EditProfileErrors;
import ru.spring.app.engine.entity.User;
import ru.spring.app.engine.repository.UserRepository;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public EditProfileResponse editProfile(EditProfileRequest request, Principal principal) {
        EditProfileResponse response = new EditProfileResponse();
        User currentUser = userRepository.findByEmail(principal.getName()).get();
        if (getErrors(request).isEmpty() && !currentUser.getEmail().equals(request.getEmail())) {
            userRepository.updateUserEmail(request.getEmail(), currentUser.getId());
            response.setResult(true);
        } else if (getErrors(request).isEmpty() && !currentUser.getName().equals(request.getName())) {
            userRepository.updateUserName(request.getName(), currentUser.getId());
            response.setResult(true);
        } else if (request.getPassword() != null) {
            userRepository.updateUserPassword(passwordEncoder.encode(request.getPassword()), currentUser.getId());
            response.setResult(true);
        } else {
            response.setErrors(getErrors(request));
            response.setResult(false);
        }
        return response;
    }

    private List<EditProfileErrors> getErrors(EditProfileRequest request) {
        List<EditProfileErrors> errors = new ArrayList<>();
        if (!request.getEmail().isEmpty() && userRepository.findByEmail(request.getEmail()).isEmpty()) {
            EditProfileErrors error = new EditProfileErrors();
            error.setEmail("you sent an incorrect email");
            errors.add(error);
        } else if (request.getName().isEmpty()) {
            EditProfileErrors error = new EditProfileErrors();
            error.setName("you sent incorrect name");
            errors.add(error);
        } else if (request.getPassword() != null && request.getPassword().length() < 6) {
            EditProfileErrors error = new EditProfileErrors();
            error.setPassword("you sent incorrect password");
            errors.add(error);
        }
        return errors;
    }
}
