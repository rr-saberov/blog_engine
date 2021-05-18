package ru.spring.app.engine.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.spring.app.engine.api.request.PostRequest;
import ru.spring.app.engine.api.response.CurrentPostResponse;
import ru.spring.app.engine.api.response.PostsResponse;
import ru.spring.app.engine.api.response.SinglePostResponse;
import ru.spring.app.engine.service.PostService;

import java.security.Principal;
import java.util.Date;

@RestController
@Api("post controller for rest api")
@RequestMapping("/api/post")
public class ApiPostController {

    private final PostService postService;

    @Autowired
    public ApiPostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    @ApiOperation("method to get all posts")
    public ResponseEntity<PostsResponse> getPosts(
            @RequestParam(defaultValue = "0") Integer offset,
            @RequestParam(defaultValue = "10") Integer limit,
            @RequestParam(defaultValue = "recent") String mode) {
        return ResponseEntity.ok(postService.getPosts(offset, limit, mode));
    }

    @GetMapping("/search")
    @PreAuthorize("hasAuthority('user:write')")
    @ApiOperation("method to search posts")
    public ResponseEntity<PostsResponse> searchPosts(
            @RequestParam(defaultValue = "0") Integer offset,
            @RequestParam(defaultValue = "10") Integer limit,
            @RequestParam(defaultValue = "test") String query) {
        return ResponseEntity.ok(postService.getPostsByUserRequest(offset, limit, query));
    }

    @GetMapping("/byDate")
    @PreAuthorize("hasAuthority('user:moderate')")
    @ApiOperation("method to get posts by date")
    public ResponseEntity<PostsResponse> getPostsByDate(
            @RequestParam(defaultValue = "0") Integer offset,
            @RequestParam(defaultValue = "5") Integer limit,
            @RequestParam(defaultValue = "2005-10-9")
            @DateTimeFormat(pattern="yyyy-MM-dd") Date date) {
        return ResponseEntity.ok(postService.getPostsOnDay(offset, limit, date));
    }

    @GetMapping("/byTag")
    @ApiOperation("method to get posts by tag")
    public ResponseEntity<PostsResponse> getPostsByTag(
            @RequestParam(defaultValue = "0") Integer offset,
            @RequestParam(defaultValue = "10") Integer limit,
            @RequestParam(defaultValue = "tag name") String tag) {
        return ResponseEntity.ok(postService.getPostsByTag(offset, limit, tag));
    }

    @GetMapping("/{ID}")
    @ApiOperation("method to get post by id")
    public ResponseEntity<CurrentPostResponse> postById(@PathVariable(value = "ID") Integer id) {
        return ResponseEntity.ok(postService.getPostById(id));
    }

    @GetMapping("/moderation")
    @PreAuthorize("hasAuthority('user:moderate')")
    public ResponseEntity<PostsResponse> postsForModeration(@RequestParam(defaultValue = "0") Integer offset,
                                                            @RequestParam(defaultValue = "10") Integer limit,
                                                            @RequestParam(defaultValue = "NEW") String status) {
        return ResponseEntity.ok(postService.getPostsForModeration(offset, limit, status));
    }

    @GetMapping("/my")
    @PreAuthorize("hasAuthority('user:write')")
    public ResponseEntity<PostsResponse> userPosts(@RequestParam(defaultValue = "0") Integer offset,
                                                   @RequestParam(defaultValue = "10") Integer limit,
                                                   @RequestParam(defaultValue = "published") String status,
                                                   Principal principal) {
        return ResponseEntity.ok(postService.getUserPosts(offset, limit, status, principal.getName()));
    }

    @PostMapping("/api/post")        //вопросы?
    @PreAuthorize("hasAuthority('user:write')")
    public ResponseEntity<Boolean> addPost(@RequestParam Long timestamp,
                                           @RequestParam Integer active,
                                           @RequestParam String title,
                                           @RequestParam String text,
                                           @RequestParam String tags) {
        return ResponseEntity.ok(postService.addNewPost(timestamp, active, title, text, tags));
    }

    @PutMapping("/api/post/{ID}")
    @PreAuthorize("hasAuthority('user:write')")
    public ResponseEntity<Boolean> updatePost(@PathVariable("ID") Integer id,
                                              @RequestParam Long timestamp,
                                              @RequestParam Integer active,
                                              @RequestParam String title,
                                              @RequestParam String text,
                                              @RequestParam String tags) {
        return ResponseEntity.ok(postService.updatePost(id, timestamp, active, title, text, tags));
    }
}
