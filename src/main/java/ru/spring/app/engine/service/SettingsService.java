package ru.spring.app.engine.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.spring.app.engine.api.response.SettingsResponse;
import ru.spring.app.engine.entity.GlobalSettings;
import ru.spring.app.engine.repository.GlobalSettingsRepository;

import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SettingsService {

    private GlobalSettingsRepository settingsRepository;

    @Autowired
    public SettingsService(GlobalSettingsRepository settingsRepository) {
        this.settingsRepository = settingsRepository;
    }

    public SettingsResponse getGlobalSettings() {
        SettingsResponse settingsResponse = new SettingsResponse();

        Map<String, Boolean> map = settingsRepository.findAll().stream()
                .collect(Collectors.toMap(GlobalSettings::getCode, settings -> settings.getValue().equals("YES")));

        settingsResponse.setMultiuserMode(map.get("MULTIUSER_MODE"));
        settingsResponse.setPostPremoderation(map.get("POST_MODERATION"));
        settingsResponse.setStatisticsIsPublic(map.get("STATISTICS_IS_PUBLIC"));
        return settingsResponse;
    }
}
