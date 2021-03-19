package ru.spring.app.engine.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.spring.app.engine.entity.GlobalSettings;

@Repository
public interface GlobalSettingsRepository extends JpaRepository<GlobalSettings, Long> {

}
