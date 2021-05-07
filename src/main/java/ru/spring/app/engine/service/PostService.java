package ru.spring.app.engine.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.spring.app.engine.api.response.*;
import ru.spring.app.engine.entity.ModerationStatus;
import ru.spring.app.engine.entity.Post;
import ru.spring.app.engine.repository.PostRepository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {

    private final PostRepository postRepository;

    @Autowired
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public PostsResponse getPosts(Integer offset, Integer limit, String mode) {
        Pageable nextPage = PageRequest.of(offset / limit, 5);
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

    public PostsResponse getPostsForModeration(Integer offset, Integer limit, String status) {
        Pageable nextPage = PageRequest.of(offset / limit, 5);
        switch (status) {
            case "NEW":
                return getNewPosts(nextPage);
            case "ACCEPTED":
                return getAcceptedPosts(nextPage);
            case "DECLINED":
                return getDeclinedPosts(nextPage);
            default:
                return postsByDate(nextPage);
        }
    }

    public PostsResponse getUserPosts(Integer offset, Integer limit, String status, String name) {
        Pageable nextPage = PageRequest.of(offset / limit, 5);
        switch (status) {
            case "inactive":
                return getInactivePostsByUser(nextPage, name);
            case "pending":
                return getPendingPostsByUser(nextPage, name);
            case "declined":
                return getDeclinedPostsByUser(nextPage, name);
            default:
                return getPublishedPostsByUser(nextPage, name);
        }
    }

    public PostsResponse getPostsByUserRequest(Integer offset, Integer limit, String query) {
        Pageable nextPage = PageRequest.of(offset / limit, 5);
        PostsResponse postsResponse = new PostsResponse();
        Page<Post> postsPage =
                postRepository.searchInText(query, nextPage);
        postsResponse.setCount(postsPage.getTotalElements());
        postsResponse.setPosts(postsPage.get().map(this::convertToResponse).collect(Collectors.toList()));
        return postsResponse;
    }

    public PostsResponse getPostsOnDay(Integer offset, Integer limit, Date date) {
        Pageable nextPage = PageRequest.of(offset / limit, 5);
        PostsResponse postsResponse = new PostsResponse();
        Page<Post> postsPage =
                postRepository.getPostsPerDay(date, nextPage);
        postsResponse.setCount(postsPage.getTotalElements());
        postsResponse.setPosts(postsPage.get().map(this::convertToResponse).collect(Collectors.toList()));
        return postsResponse;
    }

    public PostsResponse getPostsByTag(Integer offset, Integer limit, String tag) {
        Pageable nextPage = PageRequest.of(offset / limit, 5);
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
        postRepository.updatePostInfo(postRepository.getPostsById(id).getViewCount() + 1, id);
        return convertToResponse(postRepository.getPostsById(id));
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

    //methods to get posts by mod status

    private PostsResponse getNewPosts(Pageable pageable) {
        PostsResponse postsResponse = new PostsResponse();
        Page<Post> postsPage =
                postRepository.getNewPosts(pageable);
        postsResponse.setCount(postsPage.getTotalElements());
        postsResponse.setPosts(postsPage.get().map(this::convertToResponse).collect(Collectors.toList()));
        return postsResponse;
    }

    private PostsResponse getAcceptedPosts(Pageable pageable) {
        PostsResponse postsResponse = new PostsResponse();
        Page<Post> postsPage =
                postRepository.getAcceptedPosts(pageable);
        postsResponse.setCount(postsPage.getTotalElements());
        postsResponse.setPosts(postsPage.get().map(this::convertToResponse).collect(Collectors.toList()));
        return postsResponse;
    }

    private PostsResponse getDeclinedPosts(Pageable pageable) {
        PostsResponse postsResponse = new PostsResponse();
        Page<Post> postsPage =
                postRepository.getDeclinedPosts(pageable);
        postsResponse.setCount(postsPage.getTotalElements());
        postsResponse.setPosts(postsPage.get().map(this::convertToResponse).collect(Collectors.toList()));
        return postsResponse;
    }

    //methods to get user posts

    private PostsResponse getInactivePostsByUser(Pageable pageable, String name) {
        PostsResponse postsResponse = new PostsResponse();
        Page<Post> postsPage =
                postRepository.getInactivePostsByUser(pageable, name);
        postsResponse.setCount(postsPage.getTotalElements());
        postsResponse.setPosts(postsPage.get().map(this::convertToResponse).collect(Collectors.toList()));
        return postsResponse;
    }

    private PostsResponse getPendingPostsByUser(Pageable pageable, String name) {
        PostsResponse postsResponse = new PostsResponse();
        Page<Post> postsPage =
                postRepository.getPendingPostsByUser(pageable, name);
        postsResponse.setCount(postsPage.getTotalElements());
        postsResponse.setPosts(postsPage.get().map(this::convertToResponse).collect(Collectors.toList()));
        return postsResponse;
    }

    private PostsResponse getDeclinedPostsByUser(Pageable pageable, String name) {
        PostsResponse postsResponse = new PostsResponse();
        Page<Post> postsPage =
                postRepository.getDeclinedPostsByUser(pageable, name);
        postsResponse.setCount(postsPage.getTotalElements());
        postsResponse.setPosts(postsPage.get().map(this::convertToResponse).collect(Collectors.toList()));
        return postsResponse;
    }

    private PostsResponse getPublishedPostsByUser(Pageable pageable, String name) {
        PostsResponse postsResponse = new PostsResponse();
        Page<Post> postsPage =
                postRepository.getPublishedPostsByUser(pageable, name);
        postsResponse.setCount(postsPage.getTotalElements());
        postsResponse.setPosts(postsPage.get().map(this::convertToResponse).collect(Collectors.toList()));
        return postsResponse;
    }

    //method to convert post

    private SinglePostResponse convertToResponse(Post post) {
        SinglePostResponse postResponse = new SinglePostResponse();
        UserResponse userResponse = new UserResponse();
        Timestamp timestamp = new Timestamp(post.getTime().getTime());
        userResponse.setId(post.getUserId());
        userResponse.setName(post.getUsersId().getName());
        postResponse.setId(post.getId());
        postResponse.setTimestamp(timestamp.getTime() / 1_000);
        postResponse.setTitle(post.getText());
        postResponse.setAnnounce(post.getText());
        postResponse.setLikeCount(post.getPostVotes().stream().filter(v -> v.getValue() == 1).count());
        postResponse.setDislikeCount(post.getPostVotes().stream().filter(v -> v.getValue() == - 1).count());
        postResponse.setCommentCount(5);
        postResponse.setViewCount(post.getViewCount());
        postResponse.setUserResponse(userResponse);
        return postResponse;
    }
}
