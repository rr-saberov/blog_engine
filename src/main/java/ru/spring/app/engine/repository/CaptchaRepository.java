package ru.spring.app.engine.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.spring.app.engine.entity.CaptchaCodes;

@Repository
public interface CaptchaRepository extends JpaRepository<CaptchaCodes, Long> {

}
