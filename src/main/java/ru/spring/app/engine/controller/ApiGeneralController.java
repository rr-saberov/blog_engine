package ru.spring.app.engine.controller;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import ru.spring.app.engine.api.response.InitResponse;
import ru.spring.app.engine.api.response.SettingsResponse;
import ru.spring.app.engine.api.response.TagResponse;
import ru.spring.app.engine.entity.Posts;
import ru.spring.app.engine.service.PostService;
import ru.spring.app.engine.service.SettingsService;
import ru.spring.app.engine.service.TagService;

import java.util.Date;

@Api
@RestController
@RequestMapping("/api")
public class ApiGeneralController {

    private final SettingsService settingsService;
    private final TagService tagService;
    private final PostService postService;

    @Autowired
    public ApiGeneralController(SettingsService settingsService,
                                TagService tagService, PostService postService) {
        this.settingsService = settingsService;
        this.tagService = tagService;
        this.postService = postService;
    }

    @GetMapping("/init")
    private InitResponse init(@RequestBody InitResponse initResponse) {
        return initResponse;
    }

    @GetMapping("/settings")
    private SettingsResponse settings() {
        return settingsService.getGlobalSettings();
    }

    @GetMapping("/calendar")
    private Page<Posts> getPostCountInYear(@RequestParam(defaultValue = "0") Integer offset, Integer limit, Date date) {
        return postService.getPostsCountInYear(offset, limit, date);
    }

    @GetMapping("/tag")
    private TagResponse tags() {
        return tagService.getTags();
    }
}
