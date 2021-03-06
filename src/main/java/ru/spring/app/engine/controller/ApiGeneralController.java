package ru.spring.app.engine.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.spring.app.engine.api.response.InitResponse;
import ru.spring.app.engine.api.response.SettingsResponse;
import ru.spring.app.engine.api.response.TagResponse;
import ru.spring.app.engine.model.Posts;
import ru.spring.app.engine.repository.GlobalSettingsRepository;
import ru.spring.app.engine.service.PostService;
import ru.spring.app.engine.service.SettingsService;
import ru.spring.app.engine.service.TagService;

import java.util.Date;

@RestController
@RequestMapping("/api")
public class ApiGeneralController {

    private final InitResponse initResponse;
    private final SettingsService settingsService;
    private final TagService tagService;
    private final GlobalSettingsRepository repository;
    private final PostService postService;

    public ApiGeneralController(InitResponse initResponse, SettingsService settingsService, TagService tagService, GlobalSettingsRepository repository, PostService postService) {
        this.initResponse = initResponse;
        this.settingsService = settingsService;
        this.tagService = tagService;
        this.repository = repository;
        this.postService = postService;
    }

    @GetMapping("/init")
    private InitResponse init() {
        return initResponse;
    }

    @GetMapping("/settings")
    private ResponseEntity<SettingsResponse> settings() {
        return ResponseEntity.ok(settingsService.getGlobalSettings(repository));

    }

    @GetMapping("/calendar")
    private Page<Posts> getPostCountInYear(Integer limit, Date date) {
        return postService.getPostsCountInYear(limit, date);
    }

    @GetMapping("/tag")
    private ResponseEntity<TagResponse> tags() {
        return ResponseEntity.ok(tagService.getTags());
    }
}
