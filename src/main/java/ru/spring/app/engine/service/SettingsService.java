package ru.spring.app.engine.service;

import org.springframework.stereotype.Service;
import ru.spring.app.engine.api.response.SettingsResponse;
import ru.spring.app.engine.model.GlobalSettings;
import ru.spring.app.engine.repository.GlobalSettingsRepository;

import java.util.HashMap;
import java.util.Map;

@Service
public class SettingsService {

    public SettingsResponse getGlobalSettings(GlobalSettingsRepository repository) {
        SettingsResponse settingsResponse = new SettingsResponse();
        Map<String, Boolean> map = new HashMap<>();

        for (GlobalSettings globalSettings : repository.findAll()) {
            map.put(globalSettings.getCode(), globalSettings.getValue().equals("YES"));
        }

        settingsResponse.setMultiuserMode(map.get("MULTIUSER_MODE"));
        settingsResponse.setPostPremoderation(map.get("POST_MODERATION"));
        settingsResponse.setStatisticsIsPublic(map.get("STATISTICS_IS_PUBLIC"));
        return settingsResponse;
    }
}
