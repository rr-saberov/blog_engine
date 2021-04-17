package ru.spring.app.engine.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.spring.app.engine.api.response.TagsResponse;
import ru.spring.app.engine.service.TagService;

@Controller
public class ApiTagController {

    private final TagService tagService;

    @Autowired
    public ApiTagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping("/api/tag")
    public ResponseEntity<TagsResponse> tags(@RequestParam(defaultValue = "") String query) {
        return ResponseEntity.ok(tagService.getTags(query));
    }
}
