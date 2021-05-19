package ru.spring.app.engine.service;

import com.github.cage.Cage;
import com.github.cage.GCage;
import net.bytebuddy.utility.RandomString;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.spring.app.engine.api.response.CaptchaResponse;
import ru.spring.app.engine.entity.Captcha;
import ru.spring.app.engine.repository.CaptchaRepository;

import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;
import java.util.stream.Collectors;

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

    public boolean validCaptcha(String secretCode) throws IOException {
        generateCaptcha();
        Captcha captcha = captchaRepository.findAll().stream()
                .filter(el -> el.getSecretCode().equals(secretCode)).collect(Collectors.toList()).get(0);

        byte[] decodedBytes = Base64.getDecoder().decode(captcha.getSecretCode());
        FileUtils.writeByteArrayToFile(new File("captcha"), decodedBytes);

        return captcha.getSecretCode().equals(secretCode);
    }

    private void restoreOldCaptcha() {
        captchaRepository.findAll().forEach(cp -> {
            if(cp.getTime().before(new Date(System.currentTimeMillis() - 3600 * 1000)))
                captchaRepository.delete(cp);
        });
    }
}
