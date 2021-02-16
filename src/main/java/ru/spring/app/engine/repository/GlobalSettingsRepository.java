package ru.spring.app.engine.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.spring.app.engine.model.GlobalSettings;

public interface GlobalSettingsRepository extends JpaRepository <GlobalSettings, Integer> {

}
