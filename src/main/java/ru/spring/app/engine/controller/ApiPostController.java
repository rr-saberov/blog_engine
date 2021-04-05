package ru.spring.app.engine.controller;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.spring.app.engine.api.response.PostsResponse;
import ru.spring.app.engine.entity.Posts;
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

//    @GetMapping
//    private ResponseEntity<PostsResponse> getPosts(
//            @RequestParam(defaultValue = "0") Integer offset,
//            @RequestParam(defaultValue = "10") Integer limit,
//            @RequestParam(defaultValue = "recent") String mode) {
//        return ResponseEntity.ok(postService.getPosts(offset, limit, mode));
//    }


/*
    @GetMapping("/search")
    private ResponseEntity<PostsResponse> searchPosts(
            @RequestParam(defaultValue = "0") Integer offset,
            @RequestParam(defaultValue = "10") Integer limit,
            @RequestParam(defaultValue = "recent") String query) {
        return postService.search(offset, limit, query);
    }
*/

/*        switch (mode) {
            case "popular":
                return ResponseEntity.ok(postService.getPostsByCommentCount(offset, limit));
            case "best":
                return ResponseEntity.ok(postService.getPostsByLike(offset, limit));
            case "early":
                return ResponseEntity.ok(postService.getAllOldPostsByDate(offset, limit));
            default:
                return ResponseEntity.ok(postService.getAllPostsByDate(offset, limit));
        }*/


/*    @GetMapping("/byDate")
    private Page<Posts> getPostsByDate(@RequestParam(defaultValue = "0") Integer offset,
                                       @RequestParam(defaultValue = "5") Integer limit, @RequestParam(defaultValue = "2005-10-09") Date date) {
        return postService.getPostsByDate(offset, limit, date);
    }*/

//    @GetMapping("/byTag")
//    private Page<Posts> getPostsByTag(@RequestParam(defaultValue = "0") Integer offset,
//                                      @RequestParam(defaultValue = "10") Integer limit,
//                                      @RequestParam(defaultValue = "1") Integer postId) {
//        return postService.getPostsByTag(offset, limit, postId);
//    }
//
//    @GetMapping("/{ID}")
//    private Posts postById(@RequestParam(value = "ID", defaultValue = "1") Integer id) {
//        return postService.getPostById(id);
//    }
}
