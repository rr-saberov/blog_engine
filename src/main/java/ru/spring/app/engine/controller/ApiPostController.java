package ru.spring.app.engine.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.spring.app.engine.api.response.PostResponse;
import ru.spring.app.engine.model.Posts;
import ru.spring.app.engine.service.PostService;
import ru.spring.app.engine.service.TagService;

import java.util.Date;

@RestController
@RequestMapping("/api/post")
public class ApiPostController {

    private final PostService postService;
    private final TagService tagService;

    public ApiPostController(PostService postService, TagService tagService) {
        this.postService = postService;
        this.tagService = tagService;
    }

    @GetMapping
    private ResponseEntity<PostResponse> posts() {
        return ResponseEntity.ok(postService.getPosts());
    }

    @GetMapping("/search")
    private Page<Posts> getAllPost(Integer limit) {
        return postService.getAllPosts(limit);
    }

    @GetMapping("/search")
    private Page<Posts> getPostsSortedByUsers(Integer limit) {
        return postService.getPostsSortedByUser(limit);
    }

    @GetMapping("/search")
    private Page<Posts> getPostsSortedByLikeCount(Integer limit) {
        return postService.getPostsByLikeCount(limit);
    }

    @GetMapping("/search")
    private Page<Posts> getPostsByUser(Integer limit, Integer userId) {
        return postService.getPostsByUser(limit, userId);
    }



    @GetMapping("/byDate")
    private Page<Posts> getPostsByDate(Integer limit, Date date) {
        return postService.getPostsByDate(limit, date);
    }

    @GetMapping("/byTag")
    private Page<Posts> getPostsByTag(Integer limit, Integer postId) {
        return postService.getPostsByTag(limit, postId);
    }

    @GetMapping("/{ID}")
    private Posts postById(@RequestParam(value = "ID") Integer id) {
        return postService.getPostById(id);
    }
}
