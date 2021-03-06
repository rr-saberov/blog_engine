package ru.spring.app.engine.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.spring.app.engine.api.response.PostResponse;
import ru.spring.app.engine.model.Posts;
import ru.spring.app.engine.repository.PostRepository;
import ru.spring.app.engine.repository.PostsJpaRepository;

import java.util.Date;

@Service
public class PostService {

    private PostRepository postRepository;
    private PostsJpaRepository postsJpaRepository;

    public PostResponse getPosts() {
        PostResponse postResponse = new PostResponse();
        postResponse.setAnnounce("test");
        postResponse.setCommentCount(5);
        return postResponse;
    }

    public Page<Posts> getAllPosts(Integer limit) {
        return postRepository.findAll(PageRequest.of(0, limit));
    }

    public Page<Posts> getPostsSortedByUser(Integer limit) {
        return postRepository.findPostsOrderByUserId(PageRequest.of(0, limit));
    }

    public Page<Posts> getPostsByLikeCount(Integer limit) {
        return postRepository.findPostsOrderByLikes(PageRequest.of(0, limit));
    }

    public Page<Posts> getPostsByUser(Integer limit, Integer userId) {
        return postRepository.findPostsByUserId(PageRequest.of(0, limit), userId);
    }

    public Page<Posts> getPostsCountInYear(Integer limit, Date date) {
        return postRepository.postCountByYear(PageRequest.of(0, limit), date.getYear());
    }

    public Page<Posts> getPostsByDate(Integer limit, Date date) {
        return postRepository.findPostsByDate(PageRequest.of(0, limit), date);
    }

    public Page<Posts> getPostsByTag(Integer limit, Integer tagId) {
        return postRepository.findPostsByTag(PageRequest.of(0, limit), tagId);
    }

    public Posts getPostById(Integer id) {
        return postsJpaRepository.getOne(id);
    }
}
