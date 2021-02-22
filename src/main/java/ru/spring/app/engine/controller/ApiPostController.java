package ru.spring.app.engine.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.spring.app.engine.api.response.PostResponse;
import ru.spring.app.engine.model.Posts;
import ru.spring.app.engine.model.Tags;
import ru.spring.app.engine.service.PostService;
import ru.spring.app.engine.service.TagService;

import java.util.Date;
import java.util.List;
import java.util.Map;

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
    private List<Posts> searchPost(String query) {
        return postService.getSearchedPosts(query);
    }

    @GetMapping("/byDate")
    private Map<Integer, Date> postsByDate(Date date) {
        return postService.getPostsByDate(date);
    }

    @GetMapping("/byTag")
    private Map<Integer, Tags> postsByTag(Tags tags) {
        return tagService.getPostsByTag(tags);
    }

    @GetMapping("/{ID}")
    private Posts postById(Integer id) {
        return postService.getPostById(id);
    }
}
