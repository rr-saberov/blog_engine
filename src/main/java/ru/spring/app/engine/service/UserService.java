package ru.spring.app.engine.service;

import org.springframework.stereotype.Service;
import ru.spring.app.engine.api.request.EditProfileRequest;
import ru.spring.app.engine.api.response.EditProfileResponse;
import ru.spring.app.engine.repository.UserRepository;

import java.security.Principal;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public EditProfileResponse updateProfile(EditProfileRequest request, Principal principal) {
        if (!request.getEmail().isEmpty()) {
            userRepository.updateUserEmail(request.getEmail(), userRepository.getUserIdByEmail(principal.getName()));
        }
        if (!request.getName().isEmpty()) {
            userRepository.updateUserName(request.getName(), userRepository.getUserIdByEmail(principal.getName()));
        }
        if (!request.getPassword().isEmpty()) {
            userRepository.updateUserPassword(request.getPassword(), userRepository.getUserIdByEmail(principal.getName()));
        }

        EditProfileResponse response = new EditProfileResponse();
        return response;
    }
}
