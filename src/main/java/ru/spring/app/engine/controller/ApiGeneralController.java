package ru.spring.app.engine.controller;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.spring.app.engine.api.response.InitResponse;
import ru.spring.app.engine.api.response.SettingsResponse;
import ru.spring.app.engine.api.response.TagsResponse;
import ru.spring.app.engine.service.PostService;
import ru.spring.app.engine.service.SettingsService;
import ru.spring.app.engine.service.TagService;

@Api
@RestController
@RequestMapping("/api")
public class ApiGeneralController {

    private final SettingsService settingsService;
    private final TagService tagService;
    private final PostService postService;
    private final InitResponse initResponse;

    @Autowired
    public ApiGeneralController(SettingsService settingsService,
                                TagService tagService, PostService postService, InitResponse initResponse) {
        this.settingsService = settingsService;
        this.tagService = tagService;
        this.postService = postService;
        this.initResponse = initResponse;
    }

    @GetMapping("/init")
    private ResponseEntity<InitResponse> init() {
        return ResponseEntity.ok(initResponse);
    }

    @GetMapping("/settings")
    private ResponseEntity<SettingsResponse> settings() {
        return ResponseEntity.ok(settingsService.getGlobalSettings());
    }

    @GetMapping("/tag")
    private ResponseEntity<TagsResponse> tags(@RequestParam(defaultValue = "") String query) {
        return ResponseEntity.ok(tagService.getTags(query));
    }

//    @GetMapping("/calendar")
//    private ResponseEntity<Integer> getPostCountInYear(@RequestParam(defaultValue = "2021") Integer year) {
//        return ResponseEntity.ok(postService.getPostsCountInYear(year));
//    }
}
