package ru.spring.app.engine.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;
import ru.spring.app.engine.api.request.CommentRequest;
import ru.spring.app.engine.api.request.PostRequest;
import ru.spring.app.engine.api.response.*;
import ru.spring.app.engine.exceptions.PostNotFoundException;
import ru.spring.app.engine.service.CommentService;
import ru.spring.app.engine.service.PostService;

import java.security.Principal;
import java.util.Date;

@RestController
@RequestMapping("/api")
@Api("post controller for rest api")
public class ApiPostController {

    private final PostService postService;
    private final CommentService commentsService;

    public ApiPostController(PostService postService, CommentService commentsService) {
        this.postService = postService;
        this.commentsService = commentsService;
    }

    @GetMapping("/post")
    @ApiOperation("method to get all posts")
    public ResponseEntity<PostsResponse> getPosts(
            @RequestParam(defaultValue = "0") Integer offset,
            @RequestParam(defaultValue = "10") Integer limit,
            @RequestParam(defaultValue = "recent") String mode) {
        return ResponseEntity.ok(postService.getPosts(offset, limit, mode));
    }

    @GetMapping("/post/search")
    @ApiOperation("method to search posts")
    public ResponseEntity<PostsResponse> searchPosts(
            @RequestParam(defaultValue = "0") Integer offset,
            @RequestParam(defaultValue = "10") Integer limit,
            @RequestParam(defaultValue = "test") String query) {
        return ResponseEntity.ok(postService.getPostsByUserRequest(offset, limit, query));
    }

    @GetMapping("/post/byDate")
    @ApiOperation("method to get posts by date")
    public ResponseEntity<PostsResponse> getPostsByDate(
            @RequestParam(defaultValue = "0") Integer offset,
            @RequestParam(defaultValue = "5") Integer limit,
            @RequestParam(defaultValue = "2005-10-9")
            @DateTimeFormat(pattern="yyyy-MM-dd") Date date) {
        return ResponseEntity.ok(postService.getPostsOnDay(offset, limit, date));
    }

    @GetMapping("/post/byTag")
    @ApiOperation("method to get posts by tag")
    public ResponseEntity<PostsResponse> getPostsByTag(
            @RequestParam(defaultValue = "0") Integer offset,
            @RequestParam(defaultValue = "10") Integer limit,
            @RequestParam(defaultValue = "java") String tag) {
        return ResponseEntity.ok(postService.getPostsByTag(offset, limit, tag));
    }

    @GetMapping("/post/{ID}")
    @ApiOperation("method to get post by id")
    public ResponseEntity<CurrentPostResponse> postById(@PathVariable(value = "ID") Long id) throws PostNotFoundException {
        return ResponseEntity.ok(postService.getPostById(id));  //сделать статус 404
    }

    @GetMapping("/post/moderation/")
    @ApiOperation("method to get posts for moderation")
    @PreAuthorize("hasAuthority('user:moderate')")
    public ResponseEntity<PostsResponse> postsForModeration(@RequestParam(defaultValue = "0") Integer offset,
                                                            @RequestParam(defaultValue = "10") Integer limit,
                                                            @RequestParam(defaultValue = "ACCEPTED") String status) {
        return ResponseEntity.ok(postService.getPostsForModeration(offset, limit, status));
    }

    @GetMapping("/post/my")
    @ApiOperation("method to get users posts")
    @PreAuthorize("hasAuthority('user:write')")
    public ResponseEntity<PostsResponse> userPosts(@RequestParam(defaultValue = "0") Integer offset,
                                                   @RequestParam(defaultValue = "10") Integer limit,
                                                   @RequestParam(defaultValue = "published") String status,
                                                   Principal principal) {
        return ResponseEntity.ok(postService.getUserPosts(offset, limit, status, principal.getName()));
    }

    @PostMapping("/post/moderation")
    @ApiOperation("method to moderate post")
    @PreAuthorize("hasAuthority('user:moderate')")
    public ResponseEntity<Boolean> moderatePost(@RequestParam Long id, @RequestParam String decision) {
        Boolean result = postService.moderatePost(id, decision);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/post")
    @ApiOperation("method to add new post")
    @PreAuthorize("hasAuthority('user:write')")
    public ResponseEntity<AddPostResponse> addPost(@RequestBody PostRequest request) {
        return ResponseEntity.ok(postService.addNewPost(request));
    }

    @PutMapping("/post/{ID}")
    @ApiOperation("method to update post")
    @PreAuthorize("hasAuthority('user:write')")
    public ResponseEntity<AddPostResponse> updatePost(@PathVariable("ID") Long id, @RequestBody PostRequest request) {
        return ResponseEntity.ok(postService.updatePost(id, request));
    }

    @PostMapping("/comment")
    @ApiOperation("method to add comment")
    @PreAuthorize("hasAuthority('user:write')")
    public ResponseEntity<AddCommentResponse> addComment(@RequestBody CommentRequest comment, Principal principal) {
        return ResponseEntity.ok(commentsService.addComment(comment, principal.getName()));
    }

    @PostMapping("/post/like")
    @ApiOperation("method to add like")
    @PreAuthorize("hasAuthority('user:write')")
    public ResponseEntity<Boolean> addLike(@RequestParam Long postId, Principal principal) {
        Boolean result = postService.addLike(postId, principal.getName());
        return ResponseEntity.ok(result);
    }

    @PostMapping("/post/dislike")
    @ApiOperation("method to add dislike")
    @PreAuthorize("hasAuthority('user:write')")
    public ResponseEntity<Boolean> addDislike(@RequestParam Long postId, Principal principal) {
        Boolean result = postService.addDislike(postId, principal.getName());
        return ResponseEntity.ok(result);
    }
}