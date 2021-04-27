package ru.spring.app.engine.service;

import com.github.cage.Cage;
import com.github.cage.GCage;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Service;
import ru.spring.app.engine.api.response.CaptchaResponse;
import ru.spring.app.engine.entity.CaptchaCodes;
import ru.spring.app.engine.repository.CaptchaRepository;

import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.stream.Collectors;

@Service
public class CaptchaService {

    private final JdbcTemplate jdbcTemplate;
    private final CaptchaRepository captchaRepository;

    @Autowired
    public CaptchaService(JdbcTemplate jdbcTemplate, CaptchaRepository captchaRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.captchaRepository = captchaRepository;
    }

    public CaptchaResponse generateCaptcha() {
        String encodedString;
        Cage cage = new GCage();
        CaptchaResponse response = new CaptchaResponse();
        String token = cage.getTokenGenerator().next();
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();

        byte[] fileContent = cage.draw(token);
        encodedString = "data:image/png;base64, " + Base64.getEncoder().encodeToString(fileContent);

        parameterSource.addValue("date", java.time.LocalDateTime.now());
        parameterSource.addValue("code", token);
        parameterSource.addValue("secret_code", encodedString);

        response.setSecret(token);
        response.setImage(encodedString);

        jdbcTemplate.update("INSERT INTO captcha_codes(date, code, secret_code) " +
                "VALUES (:date, :code, :secret_code)", parameterSource);

        return response;
    }

    public boolean validCaptcha(String code) throws IOException {
        generateCaptcha();
        CaptchaCodes captchaCodes = captchaRepository.findAll().stream()
                .filter(captcha -> captcha.getCode().equals(code)).collect(Collectors.toList()).get(0);

        byte[] decodedBytes = Base64.getDecoder().decode(captchaCodes.getSecretCode());
        FileUtils.writeByteArrayToFile(new File("captcha"), decodedBytes);

        return captchaCodes.getCode().equals(code);
    }
}
