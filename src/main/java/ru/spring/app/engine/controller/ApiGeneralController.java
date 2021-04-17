package ru.spring.app.engine.controller;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.spring.app.engine.api.response.CalendarResponse;
import ru.spring.app.engine.api.response.InitResponse;
import ru.spring.app.engine.api.response.SettingsResponse;
import ru.spring.app.engine.service.PostService;
import ru.spring.app.engine.service.SettingsService;

@Api
@RestController
@RequestMapping("/api")
public class ApiGeneralController {

    private final SettingsService settingsService;
    private final PostService postService;
    private final InitResponse initResponse;

    @Autowired
    public ApiGeneralController(SettingsService settingsService,
                                PostService postService, InitResponse initResponse) {
        this.settingsService = settingsService;
        this.postService = postService;
        this.initResponse = initResponse;
    }

    @GetMapping("/init")
    public ResponseEntity<InitResponse> init() {
        return ResponseEntity.ok(initResponse);
    }

    @GetMapping("/settings")
    public ResponseEntity<SettingsResponse> settings() {
        return ResponseEntity.ok(settingsService.getGlobalSettings());
    }

    @GetMapping("/calendar")
    public ResponseEntity<CalendarResponse> getPostCountInYear(@RequestParam(defaultValue = "2005") Integer year) {
        return ResponseEntity.ok(postService.getPostsCountInTheYear(year));
    }
}
