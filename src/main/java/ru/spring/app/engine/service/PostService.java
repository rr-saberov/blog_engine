package ru.spring.app.engine.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.spring.app.engine.entity.Posts;
import ru.spring.app.engine.repository.PostRepository;

import java.util.Date;

@Service
public class PostService {

    private PostRepository postRepository;

    public Page<Posts> getAllPosts(Integer offset, Integer limit) {
        return postRepository.findAll(PageRequest.of(offset, limit));
    }

    public Page<Posts> getAllPostsByDate(Integer offset, Integer limit) {
        return postRepository.getAllPostsByDate(PageRequest.of(offset, limit));
    }

    public Page<Posts> getAllOldPostsByDate(Integer offset, Integer limit) {
        return postRepository.getAllOldPostsByDate(PageRequest.of(offset, limit));
    }

    public Page<Posts> getPostsByLike(Integer offset, Integer limit) {
        return postRepository.getPostsByLikes(PageRequest.of(offset, limit));
    }

    public Page<Posts> getPostsByCommentCount(Integer offset, Integer limit) {
        return postRepository.getPostsByCommentCount(PageRequest.of(offset, limit));
    }

    public Page<Posts> getPostsCountInYear(Integer offset, Integer limit, Date date) {
        return postRepository.postCountByYear(PageRequest.of(offset, limit), date.getYear());
    }

    public Page<Posts> getPostsByDate(Integer offset, Integer limit, Date date) {
        return postRepository.findPostsByDate(PageRequest.of(offset, limit), date);
    }

    public Page<Posts> getPostsByTag(Integer offset, Integer limit, Integer tagId) {
        return postRepository.findPostsByTag(PageRequest.of(offset, limit), tagId);
    }

    public Posts getPostById(Integer id) {
        return postRepository.getOne(id);
    }
}
