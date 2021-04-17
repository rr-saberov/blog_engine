package ru.spring.app.engine.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.spring.app.engine.api.response.*;
import ru.spring.app.engine.entity.Post;
import ru.spring.app.engine.repository.PostRepository;

import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PostService {

    private final PostRepository postRepository;

    @Autowired
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public PostsResponse getAllPosts() {
        PostsResponse postsResponse = new PostsResponse();
        postsResponse.setPosts(Collections.emptyList());
        return postsResponse;
    }

    public PostsResponse getPosts(Integer page, Integer limit, String mode) {
        Pageable nextPage = PageRequest.of(page, limit);
        switch (mode) {
            case "popular":
                return postsByCommentCount(nextPage);
            case "best":
                return postsByLikeCount(nextPage);
            case "early":
                return oldPostsByDate(nextPage);
            default:
                return postsByDate(nextPage);
        }
    }

    public PostsResponse getPostsByUserRequest(Integer page, Integer limit, String query) {
        Pageable nextPage = PageRequest.of(page, limit);
        PostsResponse postsResponse = new PostsResponse();
        Page<Post> postsPage =
                postRepository.getPostsByCustomRequest(query + "%", nextPage);
        postsResponse.setCount(postsPage.getTotalElements());
        postsResponse.setPosts(postsPage.get().map(this::convertToResponse).collect(Collectors.toList()));
        return postsResponse;
    }

    public PostsResponse getPostsOnDay(Integer page, Integer limit, Date date) {
        Pageable nextPage = PageRequest.of(page, limit);
        PostsResponse postsResponse = new PostsResponse();
        Page<Post> postsPage =
                postRepository.getPostsPerDay(date, nextPage);
        postsResponse.setCount(postsPage.getTotalElements());
        postsResponse.setPosts(postsPage.get().map(this::convertToResponse).collect(Collectors.toList()));
        return postsResponse;
    }

    public PostsResponse getPostsByTag(Integer page, Integer limit, String tag) {
        Pageable nextPage = PageRequest.of(page, limit);
        PostsResponse postsResponse = new PostsResponse();
        Page<Post> postsPage =
                postRepository.getPostsWithTag(tag, nextPage);
        postsResponse.setCount(postsPage.getTotalElements());
        postsResponse.setPosts(postsPage.get().map(this::convertToResponse).collect(Collectors.toList()));
        return postsResponse;
    }

    public CalendarResponse getPostsCountInTheYear(Integer year) {
        CalendarResponse calendarResponse;
        if (year == 0) {
            calendarResponse = convertMapToResponse();
        } else {
            calendarResponse = convertMapToResponse(year);
        }
        return calendarResponse;
    }

    public SinglePostResponse getPostById(Integer id) {
        Post post = postRepository.getPostsById(id);
        return convertToResponse(post);
    }

    //methods for get responses

    private CalendarResponse convertMapToResponse() {
        CalendarResponse calendarResponse = new CalendarResponse();
        List<PostInDayResponse> postInDayResponses = new ArrayList<>();
        postRepository.getPostsCountOnTheDay().forEach((key, value) -> {
            PostInDayResponse response = new PostInDayResponse(key, value);
            postInDayResponses.add(response);
        });
        calendarResponse.setPosts(postInDayResponses);
        return calendarResponse;
    }

    private CalendarResponse convertMapToResponse(Integer year) {
        CalendarResponse calendarResponse = new CalendarResponse();
        List<PostInDayResponse> postInDayResponses = new ArrayList<>();
        postRepository.getPostsInYear(year).forEach((key, value) -> {
            PostInDayResponse response = new PostInDayResponse(key, value);
            postInDayResponses.add(response);
        });
        calendarResponse.setPosts(postInDayResponses);
        return calendarResponse;
    }

    private PostsResponse postsByCommentCount(Pageable pageable) {
        PostsResponse postsResponse = new PostsResponse();
        Page<Post> postsPage =
                postRepository.getPostsOrderByCommentCount(pageable);
        postsResponse.setCount(postsPage.getTotalElements());
        postsResponse.setPosts(postsPage.get().map(this::convertToResponse).collect(Collectors.toList()));
        return postsResponse;
    }

    private PostsResponse postsByLikeCount(Pageable pageable) {
        PostsResponse postsResponse = new PostsResponse();
        Page<Post> postsPage =
                postRepository.getPostsOrderByLikeCount(pageable);
        postsResponse.setCount(postsPage.getTotalElements());
        postsResponse.setPosts(postsPage.get().map(this::convertToResponse).collect(Collectors.toList()));
        return postsResponse;
    }

    private PostsResponse postsByDate(Pageable pageable) {
        PostsResponse postsResponse = new PostsResponse();
        Page<Post> postsPage =
                postRepository.getPostsOrderByTime(pageable);
        postsResponse.setCount(postsPage.getTotalElements());
        postsResponse.setPosts(postsPage.get().map(this::convertToResponse).collect(Collectors.toList()));
        return postsResponse;
    }

    private PostsResponse oldPostsByDate(Pageable pageable) {
        PostsResponse postsResponse = new PostsResponse();
        Page<Post> postsPage =
                postRepository.getOldPostsOrderByTime(pageable);
        postsResponse.setCount(postsPage.getTotalElements());
        postsResponse.setPosts(postsPage.get().map(this::convertToResponse).collect(Collectors.toList()));
        return postsResponse;
    }

    private SinglePostResponse convertToResponse(Post post) {
        SinglePostResponse postResponse = new SinglePostResponse();
        UserResponse userResponse = new UserResponse();
        Timestamp timestamp = new Timestamp(post.getTime().getTime());
        userResponse.setId(post.getUserId());
        userResponse.setName(post.getUsersId().getName());
        postResponse.setId(post.getId());
        postResponse.setTimestamp(timestamp.getTime());
        postResponse.setTitle(post.getText());
        postResponse.setAnnounce(post.getText());
        postResponse.setLikeCount(post.getPostVotes().getValue());
        postResponse.setDislikeCount(4);
        postResponse.setCommentCount(5);
        postResponse.setViewCount(post.getViewCount());
        postResponse.setUserResponse(userResponse);
        return postResponse;
    }
}
