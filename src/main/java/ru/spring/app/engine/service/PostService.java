package ru.spring.app.engine.service;

import liquibase.pro.packaged.D;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.spring.app.engine.api.response.*;
import ru.spring.app.engine.entity.ModerationStatus;
import ru.spring.app.engine.entity.Post;
import ru.spring.app.engine.entity.Tags;
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
        Pageable nextPage = PageRequest.of(offset / limit, limit);
        Page<Post> page = switch (mode) {
            case "popular" ->
                postRepository.getPostsOrderByCommentCount(nextPage);
            case "best" ->
                postRepository.getPostsOrderByLikeCount(nextPage);
            case "early" ->
                postRepository.getOldPostsOrderByTime(nextPage);
            default ->
                postRepository.getPostsOrderByTime(nextPage);
        };
        return convertPagePostsToResponse(page);
    }

    public PostsResponse getPostsForModeration(Integer offset, Integer limit, String status) {
        Pageable nextPage = PageRequest.of(offset / limit, limit);
        Page<Post> page = switch (status) {
            case "NEW" ->
                postRepository.getNewPosts(nextPage);
            case "ACCEPTED" ->
                postRepository.getAcceptedPosts(nextPage);
            case "DECLINED" ->
                postRepository.getDeclinedPosts(nextPage);
            default ->
               postRepository.getPostsOrderByTime(nextPage);
        };
        return convertPagePostsToResponse(page);
    }

    public PostsResponse getUserPosts(Integer offset, Integer limit, String status, String email) {
        Pageable nextPage = PageRequest.of(offset / limit, limit);
        Page<Post> page = switch (status) {
            case "inactive" ->
                postRepository.getInactivePostsByUser(nextPage, email);
            case "pending" ->
                postRepository.getPendingPostsByUser(nextPage, email);
            case "declined" ->
                postRepository.getDeclinedPostsByUser(nextPage, email);
            default ->
                postRepository.getPublishedPostsByUser(nextPage, email);
        };
        return convertPagePostsToResponse(page);
    }

    public PostsResponse getPostsByUserRequest(Integer offset, Integer limit, String query) {
        Pageable nextPage = PageRequest.of(offset / limit, limit);
        PostsResponse postsResponse = new PostsResponse();
        Page<Post> postsPage =
                postRepository.searchInText(query, nextPage);
        postsResponse.setCount(postsPage.getTotalElements());
        postsResponse.setPosts(postsPage.get().map(this::convertPostToSingleResponse).collect(Collectors.toList()));
        return postsResponse;
    }

    public PostsResponse getPostsOnDay(Integer offset, Integer limit, Date date) {
        Pageable nextPage = PageRequest.of(offset / limit, limit);
        PostsResponse postsResponse = new PostsResponse();
        Page<Post> postsPage =
                postRepository.getPostsPerDay(date, nextPage);
        postsResponse.setCount(postsPage.getTotalElements());
        postsResponse.setPosts(postsPage.get().map(this::convertPostToSingleResponse).collect(Collectors.toList()));
        return postsResponse;
    }

    public PostsResponse getPostsByTag(Integer offset, Integer limit, String tag) {
        Pageable nextPage = PageRequest.of(offset / limit, limit);
        PostsResponse postsResponse = new PostsResponse();
        Page<Post> postsPage =
                postRepository.getPostsWithTag(tag, nextPage);
        postsResponse.setCount(postsPage.getTotalElements());
        postsResponse.setPosts(postsPage.get().map(this::convertPostToSingleResponse).collect(Collectors.toList()));
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

    public CurrentPostResponse getPostById(Integer id) {
        postRepository.updatePostInfo(postRepository.getPostsById(id).getViewCount() + 1, id);
        return convertPostToCurrentPostResponse(postRepository.getPostsById(id));
    }

    public Boolean addNewPost(Long timestamp, Integer active, String title, String text, String tags) {
        Post post = new Post();

        if (new Date(timestamp).after(new Date())) {
            post.setTime(new Date(timestamp));
        } else {
            post.setTime(new Date());
        }

        post.setIsActive(active);
        post.setText(text);
        post.setModerationStatus(ModerationStatus.NEW);

        return false;
    }

    public Boolean updatePost(Integer id, Long timestamp, Integer active, String title, String text, String tags) {
        return false;
    }

    //methods to convert posts

    private PostsResponse convertPagePostsToResponse(Page<Post> posts) {
        PostsResponse postsResponse = new PostsResponse();
        postsResponse.setCount(posts.getTotalElements());
        postsResponse.setPosts(posts.stream().map(this::convertPostToSingleResponse).collect(Collectors.toList()));
        return postsResponse;
    }

    private SinglePostResponse convertPostToSingleResponse(Post post) {
        SinglePostResponse postResponse = new SinglePostResponse();
        UserResponse userResponse = new UserResponse();
        Timestamp timestamp = new Timestamp(post.getTime().getTime());
        userResponse.setId(post.getUserId());
        userResponse.setName(post.getUsersId().getName());
        postResponse.setId(post.getId());
        postResponse.setTimestamp(timestamp.getTime() / 1_000);
        postResponse.setTitle(post.getText());
        postResponse.setAnnounce(post.getText());
        postResponse.setLikeCount(post.getPostVotes().stream().filter(vote -> vote.getValue() == 1).count());
        postResponse.setDislikeCount(post.getPostVotes().stream().filter(vote -> vote.getValue() == - 1).count());
        postResponse.setCommentCount(5);
        postResponse.setViewCount(post.getViewCount());
        postResponse.setUserResponse(userResponse);
        return postResponse;
    }

    private CurrentPostResponse convertPostToCurrentPostResponse(Post post) {   //комментарии
        CurrentPostResponse postResponse = new CurrentPostResponse();
        CommentUserResponse userResponse = new CommentUserResponse();
        userResponse.setId(post.getUserId());
        userResponse.setName(post.getUsersId().getName());
        postResponse.setId(post.getId());
        postResponse.setTimestamp(new Timestamp(post.getTime().getTime()).getTime() / 1_000);
        postResponse.setTitle(post.getText());
        postResponse.setLikeCount(post.getPostVotes().stream().filter(vote -> vote.getValue() == 1).count());
        postResponse.setDislikeCount(post.getPostVotes().stream().filter(vote -> vote.getValue() == - 1).count());
        postResponse.setViewCount(post.getViewCount());
        postResponse.setUserResponse(userResponse);
        postResponse.setActive(post.getIsActive() == 1);
        postResponse.setTags((String[]) postRepository.getTagsByPostId(post.getId())
                .stream().map(Tags::getName).map(String::new).toArray());
        postResponse.setComments(convertPostCommentsToResponse(post.getId()));
        return postResponse;
    }

    private List<CommentResponse> convertPostCommentsToResponse(Integer postId) {
        List<CommentResponse> responses = new ArrayList<>();
        postRepository.getCommentsForPost(postId).forEach(postComments -> {
            CommentResponse commentResponse = new CommentResponse();
            commentResponse.setId(postComments.getId());
            commentResponse.setText(postComments.getText());
            commentResponse.setTimestamp(postComments.getTime().getTime());
            responses.add(commentResponse);
        });
        return responses;
    }

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
}