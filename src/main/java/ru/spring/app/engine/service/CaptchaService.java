package ru.spring.app.engine.service;

import com.github.cage.Cage;
import com.github.cage.GCage;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.spring.app.engine.api.response.CaptchaResponse;
import ru.spring.app.engine.entity.Captcha;
import ru.spring.app.engine.repository.CaptchaRepository;

import java.util.Base64;
import java.util.Date;

@Service
public class CaptchaService {

    private final CaptchaRepository captchaRepository;

    @Autowired
    public CaptchaService(CaptchaRepository captchaRepository) {
        this.captchaRepository = captchaRepository;
    }

    public CaptchaResponse generateCaptcha() {
        restoreOldCaptcha();
        Cage cage = new GCage();
        String token = cage.getTokenGenerator().next();
        String secretCode = new RandomString(12).nextString();
        captchaRepository.save(new Captcha(new Date(), token, secretCode));
        byte[] fileContent = cage.draw(token);
        String encodedString = "data:image/png;base64, " + Base64.getEncoder().encodeToString(fileContent);
        return new CaptchaResponse(secretCode, encodedString);
    }

    public boolean validCaptcha(String secretCode, String currentCaptcha) {
        generateCaptcha();
        if (captchaRepository.findBySecretCode(secretCode).isPresent()) {
            Captcha captcha = captchaRepository.findBySecretCode(secretCode).get();
            return captcha.getCode().equals(currentCaptcha);
        } else {
            return false;
        }
    }

    private void restoreOldCaptcha() {
        captchaRepository.findAll().forEach(cp -> {
            if(cp.getTime().before(new Date(System.currentTimeMillis() - 3600 * 1000)))
                captchaRepository.delete(cp);
        });
    }
}
