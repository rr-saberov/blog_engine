package ru.spring.app.engine.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.spring.app.engine.api.response.PostResponse;
import ru.spring.app.engine.service.PostService;

@RestController
@RequestMapping("/api/post")
public class ApiPostController {

    private final PostService postService;

    public ApiPostController(PostService postService) {
        this.postService = postService;
    }


    @GetMapping
    private ResponseEntity<PostResponse> posts() {
        return ResponseEntity.ok(postService.getPosts());
    }
}
