package ru.spring.app.engine.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import ru.spring.app.engine.entity.Post;
import ru.spring.app.engine.repository.PostRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PostServiceTest {

    private List<Post> expectedPosts;
    private final PostRepository postRepository;
    private Pageable pageable = PageRequest.of(5, 10);

    @Autowired
    PostServiceTest(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @BeforeEach
    void setUp() {
        expectedPosts = new ArrayList<>();
        expectedPosts.addAll(postRepository.findAll());

    }

    @AfterEach
    void tearDown() {
        expectedPosts = null;
    }

    @Test
    void getPostsTest() {
//        assertArrayEquals(postRepository.getPostsOrderByCommentCount(pageable).toList().toArray(),
//                expectedPosts.stream().sorted(post -> post.));
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
}