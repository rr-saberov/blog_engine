package ru.spring.app.engine.controller;

import io.swagger.annotations.Api;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.spring.app.engine.entity.Posts;
import ru.spring.app.engine.service.PostService;

import java.util.Date;

@Api
@RestController
@RequestMapping("/api/post")
public class ApiPostController {

    private final PostService postService;

    public ApiPostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    private Page<Posts> posts(@RequestParam(defaultValue = "0") Integer offset, @RequestParam(defaultValue = "1") Integer limit) {
        return postService.getAllPosts(offset, limit);
    }

    @GetMapping("/search")
    private Page<Posts> getPosts(@RequestParam(defaultValue = "0") Integer offset,
                                 @RequestParam(defaultValue = "6") Integer limit,
                                 @RequestParam(defaultValue = "recent") String mode) {
        switch (mode) {
            case "popular" :
                return postService.getPostsByCommentCount(offset, limit);
            case "best" :
                return postService.getPostsByLike(offset, limit);
            case "early" :
                return postService.getAllOldPostsByDate(offset, limit);
            default :
                return postService.getAllPostsByDate(offset, limit);
        }
    }

    @GetMapping("/byDate")
    private Page<Posts> getPostsByDate(@RequestParam(defaultValue = "0") Integer offset,
                                       @RequestParam Integer limit, @RequestParam Date date) {
        return postService.getPostsByDate(offset, limit, date);
    }

    @GetMapping("/byTag")
    private Page<Posts> getPostsByTag(@RequestParam(defaultValue = "0") Integer offset,
                                      @RequestParam Integer limit, @RequestParam Integer postId) {
        return postService.getPostsByTag(offset, limit, postId);
    }

    @GetMapping("/{ID}")
    private Posts postById(@RequestParam(value = "ID") Integer id) {
        return postService.getPostById(id);
    }
}
