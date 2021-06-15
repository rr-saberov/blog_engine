package ru.spring.app.engine.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import ru.spring.app.engine.api.response.SinglePostResponse;
import ru.spring.app.engine.entity.Post;
import ru.spring.app.engine.repository.PostRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest
class PostServiceTest {

    List<Post> expectedList;
    final PostService postService;
    final PostRepository postRepository;

    @Autowired
    PostServiceTest(PostService postService, PostRepository postRepository) {
        this.postService = postService;
        this.postRepository = postRepository;
    }

    @BeforeEach
    void setUp() {
        expectedList = new ArrayList<>();
        expectedList.addAll(postRepository.findAll());
        Pageable nextPage = PageRequest.of(0 , 10);
    }

    @AfterEach
    void tearDown() {
        expectedList = null;
    }

    @Test
    void getPosts() {
        List<String> postsTitle =
                postService.getPosts(0, 100, "")
                        .getPosts()
                        .stream()
                        .map(SinglePostResponse::getTitle)
                        .collect(Collectors.toList());
        List<String> expectedTitles = expectedList.stream().map(Post::getText).collect(Collectors.toList());
        assertArrayEquals(postsTitle.toArray(), expectedTitles.toArray());
    }

    @Test
    void getPostsForModeration() {
    }

    @Test
    void getUserPosts() {
    }

    @Test
    void getPostsByUserRequest() {
    }

    @Test
    void getPostsOnDay() {
    }

    @Test
    void getPostsByTag() {
    }

    @Test
    void getPostsCountInTheYear() {
    }

    @Test
    void getPostById() {
    }

    @Test
    void addNewPost() {
    }

    @Test
    void updatePost() {
    }

    @Test
    void getStatistics() {
    }

    @Test
    void addLike() {
    }

    @Test
    void addDislike() {
    }

    @Test
    void moderatePost() {
    }
}