package ru.spring.app.engine.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.spring.app.engine.api.response.TagsResponse;
import ru.spring.app.engine.service.TagService;

@Api
@RestController
public class ApiTagController {

    private final TagService tagService;

    public ApiTagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping("/api/tag")
    @ApiOperation("get tags by user response")
    public ResponseEntity<TagsResponse> tags(@RequestParam(defaultValue = "") String query) {
        return ResponseEntity.ok(tagService.getTags(query));
    }
}
