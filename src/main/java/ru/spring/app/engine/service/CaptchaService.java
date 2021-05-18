package ru.spring.app.engine.service;

import com.github.cage.Cage;
import com.github.cage.GCage;
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
import java.util.stream.Collectors;

@Service
public class CaptchaService {

    private final CaptchaRepository captchaRepository;

    @Autowired
    public CaptchaService(CaptchaRepository captchaRepository) {
        this.captchaRepository = captchaRepository;
    }

    public CaptchaResponse generateCaptcha() {
        String encodedString;
        Cage cage = new GCage();
        CaptchaResponse response = new CaptchaResponse();
        String token = cage.getTokenGenerator().next();


        byte[] fileContent = cage.draw(token);
        encodedString = "data:image/png;base64, " + Base64.getEncoder().encodeToString(fileContent);

        response.setSecret(token);
        response.setImage(encodedString);

        Captcha captcha = new Captcha();
        captcha.setCode(token);
        captcha.setSecretCode(token);
        captcha.setTime(new Date());

        captchaRepository.save(captcha);

/*        captchaRepository.findAll().forEach(cp -> {
            if(cp.getTime().before(new Date()))
                captchaRepository.delete(cp);
        });*/

        return response;
    }

    public boolean validCaptcha(String code) throws IOException {
        generateCaptcha();
        Captcha captcha = captchaRepository.findAll().stream()
                .filter(el -> el.getCode().equals(code)).collect(Collectors.toList()).get(0);

        byte[] decodedBytes = Base64.getDecoder().decode(captcha.getSecretCode());
        FileUtils.writeByteArrayToFile(new File("captcha"), decodedBytes);

        return captcha.getCode().equals(code);
    }
}
