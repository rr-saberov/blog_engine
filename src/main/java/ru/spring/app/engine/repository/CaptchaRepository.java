package ru.spring.app.engine.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.spring.app.engine.entity.Captcha;

import java.util.Optional;

@Repository
public interface CaptchaRepository extends JpaRepository<Captcha, Long> {

    Optional<Captcha> findBySecretCode(String secretCode);

}
