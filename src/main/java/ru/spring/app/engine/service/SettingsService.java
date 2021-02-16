package ru.spring.app.engine.service;

import org.springframework.stereotype.Service;
import ru.spring.app.engine.api.response.SettingsResponse;
import ru.spring.app.engine.model.GlobalSettings;
import ru.spring.app.engine.repository.GlobalSettingsRepository;

@Service
public class SettingsService {

    public SettingsResponse getGlobalSettings(GlobalSettingsRepository repository) {
        SettingsResponse settingsResponse = new SettingsResponse();
        settingsResponse.setMultiuserMode(repository.getOne(1).getValue().equals("YES"));
        settingsResponse.setStatisticsIsPublic(repository.getOne(3).getValue().equals("YES"));
        return settingsResponse;
    }
}
