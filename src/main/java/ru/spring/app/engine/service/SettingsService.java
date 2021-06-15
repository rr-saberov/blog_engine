package ru.spring.app.engine.service;

import org.springframework.stereotype.Service;
import ru.spring.app.engine.api.request.SettingsRequest;
import ru.spring.app.engine.api.response.SettingsResponse;
import ru.spring.app.engine.entity.GlobalSettings;
import ru.spring.app.engine.repository.GlobalSettingsRepository;

import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SettingsService {

    private GlobalSettingsRepository settingsRepository;

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

    public Boolean updateGlobalSettings(SettingsRequest request) {
        updateMultiuserMode(request.isMultiuserMode());
        updatePostPremoderation(request.isPostPremoderation());
        updateStatisticIsPublic(request.isStatisticsIsPublic());
        return true;
    }

    private void updateMultiuserMode(boolean result) {
        if (result) {
            settingsRepository.updateMultiUserMode("YES");
        } else {
            settingsRepository.updateMultiUserMode("NO");
        }
    }

    private void updatePostPremoderation(boolean result) {
        if (result) {
            settingsRepository.updatePostPreModeration("YES");
        } else {
            settingsRepository.updatePostPreModeration("NO");
        }
    }

    private void updateStatisticIsPublic(boolean result) {
        if (result) {
            settingsRepository.updateStatisticIsPublic("YES");
        } else {
            settingsRepository.updateStatisticIsPublic("NO");
        }
    }
}
