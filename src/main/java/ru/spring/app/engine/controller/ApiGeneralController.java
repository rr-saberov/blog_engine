package ru.spring.app.engine.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.spring.app.engine.api.response.InitResponse;
import ru.spring.app.engine.api.response.SettingsResponse;
import ru.spring.app.engine.api.response.TagResponse;
import ru.spring.app.engine.repository.GlobalSettingsRepository;
import ru.spring.app.engine.service.SettingsService;
import ru.spring.app.engine.service.TagService;

@RestController
@RequestMapping("/api")
public class ApiGeneralController {

    private final InitResponse initResponse;
    private final SettingsService settingsService;
    private final TagService tagService;
    private GlobalSettingsRepository repository;

    public ApiGeneralController(InitResponse initResponse, SettingsService settingsService, TagService tagService) {
        this.initResponse = initResponse;
        this.settingsService = settingsService;
        this.tagService = tagService;
    }

    @GetMapping("/init")
    private InitResponse init() {
        return initResponse;
    }

    @GetMapping("/settings")
    private ResponseEntity<SettingsResponse> settings() {
        return ResponseEntity.ok(settingsService.getGlobalSettings(repository));

    }

    @GetMapping("/tag")
    private ResponseEntity<TagResponse> tags() {
        return ResponseEntity.ok(tagService.getTags());
    }
}
