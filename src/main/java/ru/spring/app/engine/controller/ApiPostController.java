package ru.spring.app.engine.controller;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.spring.app.engine.api.response.PostsResponse;
import ru.spring.app.engine.api.response.SinglePostResponse;
import ru.spring.app.engine.service.PostService;

import java.util.Date;

@Api
@RestController
@RequestMapping("/api/post")
public class ApiPostController {

    private final PostService postService;

    @Autowired
    public ApiPostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    private ResponseEntity<PostsResponse> getPosts(
            @RequestParam(defaultValue = "0") Integer offset,
            @RequestParam(defaultValue = "10") Integer limit,
            @RequestParam(defaultValue = "recent") String mode) {
        return ResponseEntity.ok(postService.getPosts(offset, limit, mode));
    }

    @GetMapping("/search")
    private ResponseEntity<PostsResponse> searchPosts(
            @RequestParam(defaultValue = "0") Integer offset,
            @RequestParam(defaultValue = "10") Integer limit,
            @RequestParam(defaultValue = "recent") String query) {
        return ResponseEntity.ok(postService.search(offset, limit, query));
    }

    @GetMapping("/byDate")
    private ResponseEntity<PostsResponse> getPostsByDate(
            @RequestParam(defaultValue = "0") Integer offset,
            @RequestParam(defaultValue = "5") Integer limit,
            @RequestParam(defaultValue = "2005-10-09") Date date) {
        return ResponseEntity.ok(postService.getPostsOnDay(offset, limit, date));
    }

    @GetMapping("/byTag")
    private ResponseEntity<PostsResponse> getPostsByTag(
            @RequestParam(defaultValue = "0") Integer offset,
            @RequestParam(defaultValue = "10") Integer limit,
            @RequestParam(defaultValue = "") String tag) {
        return ResponseEntity.ok(postService.getPostsByTag(offset, limit, tag));
    }

    @GetMapping("/{ID}")
    private ResponseEntity<SinglePostResponse> postById(@RequestParam(value = "ID", defaultValue = "1") Integer id) {
        return ResponseEntity.ok(postService.getPostById(id));
    }
}
