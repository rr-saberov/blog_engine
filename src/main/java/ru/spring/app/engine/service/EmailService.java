package ru.spring.app.engine.service;

import net.bytebuddy.utility.RandomString;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import ru.spring.app.engine.repository.UserRepository;

@Service
public class EmailService {

    private final JavaMailSender emailSender;
    private final UserRepository userRepository;

    public EmailService(JavaMailSender emailSender, UserRepository userRepository) {
        this.emailSender = emailSender;
        this.userRepository = userRepository;
    }

    public void sendEmail(String address, String subject, String message) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(address);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(message);
        emailSender.send(simpleMailMessage);
    }

    public Boolean restore(String email) {
        RandomString randomString = new RandomString(20);
        String message = "https://localhost:8080/login/change-password/" + randomString.nextString();
        if (userRepository.findByEmail(email).isPresent()) {
            sendEmail(email, "gfsd@gmail.com", message);
            return true;
        } else {
            return false;
        }
    }
}
