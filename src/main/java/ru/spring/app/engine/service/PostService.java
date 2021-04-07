package ru.spring.app.engine.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.spring.app.engine.api.response.*;
import ru.spring.app.engine.entity.Posts;
import ru.spring.app.engine.entity.Tags;
import ru.spring.app.engine.repository.PostRepository;

import javax.persistence.EntityManager;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PostService {

    private final PostRepository postRepository;
    private EntityManager entityManager;

    @Autowired
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public PostsResponse getAllPosts() {
        PostsResponse postsResponse = new PostsResponse();
        postsResponse.setPosts(Collections.emptyList());
        return postsResponse;
    }

    public PostsResponse getPosts(Integer offset, Integer limit, String mode) {
        Pageable nextPage = PageRequest.of(offset, limit);
        switch (mode) {
            case "popular":
                return postsByCommentCount();  //как реализовать пагинацию?
            case "best":
                return postsByLikeCount();
            case "early":
                return oldPostsByDate();
            default:
                return postsByDate();
        }
    }

    public PostsResponse search(Integer offset, Integer limit, String query) {
        return postsByCommentCount();
    }

    public CalendarResponse getPostsCountInTheYear(Integer year) {
        CalendarResponse calendarResponse = new CalendarResponse();
        if (year == 0) {
            calendarResponse.setPosts(postRepository.getPostsCountOnTheDay());
        } else {
            calendarResponse.setPosts(postRepository.getPostsInYear(year));
        }
        return calendarResponse;
    }

    public PostsResponse getPostsByTag(Integer offset, Integer limit, String tag) {
        return getPostResponseByTag(tag);
    }

/*
    public CalendarResponse getPostsByDate(String year) {
        return postRepository.getPostsCountInTheYear();
    }
*/
    private PostsResponse getPostResponseByTag(String tag) {
        PostsResponse postsResponse = new PostsResponse();
        postsResponse.setCount(postRepository.getPostsCount());
        postsResponse.setPosts(getPostResponseListByTag(tag));
        return postsResponse;
    }

    private PostsResponse getPostsByUserQuery(String query) {
        PostsResponse postsResponse = new PostsResponse();
        postsResponse.setCount(postRepository.getPostsCount());
        postsResponse.setPosts(getPostResponseListByUserQuery(query));
        return postsResponse;
    }

    private PostsResponse postsByCommentCount() {
        PostsResponse postsResponse = new PostsResponse();
        postsResponse.setCount(postRepository.getPostsCount());
        postsResponse.setPosts(getPostResponseList().stream()
                .sorted(Comparator.comparingInt(SinglePostResponse::getCommentCount))
                .collect(Collectors.toList()));
        return postsResponse;
    }

    private PostsResponse postsByLikeCount() {
        PostsResponse postsResponse = new PostsResponse();
        postsResponse.setCount(postRepository.getPostsCount());
        postsResponse.setPosts(getPostResponseList().stream()
                .sorted(Comparator.comparingInt(SinglePostResponse::getLikeCount))
                .collect(Collectors.toList()));
        return postsResponse;
    }

    private PostsResponse postsByDate() {
        PostsResponse postsResponse = new PostsResponse();
        postsResponse.setCount(postRepository.getPostsCount());
        postsResponse.setPosts(getPostResponseList().stream()
                .sorted(Comparator.comparing(SinglePostResponse::getTimestamp))
                .collect(Collectors.toList()));
        return postsResponse;
    }

    private PostsResponse oldPostsByDate() {
        PostsResponse postsResponse = new PostsResponse();
        postsResponse.setCount(postRepository.getPostsCount());
        postsResponse.setPosts(getPostResponseList().stream()
                .sorted(Comparator.comparing(SinglePostResponse::getTimestamp).reversed())
                .collect(Collectors.toList()));
        return postsResponse;
    }

    public PostsResponse getPostsOnDay(Integer offset, Integer limit, Date date) {
        PostsResponse postsResponse = new PostsResponse();
        postsResponse.setCount(postRepository.getPostsCountOnDay(date));
        postsResponse.setPosts(getPostResponseForOneDay(date));
        return postsResponse;
    }

    private List<SinglePostResponse> getPostResponseList() {
        List<SinglePostResponse> singlePostResponses = new ArrayList<>();
        postRepository.getPostsOrderByDate().forEach(posts -> {
            SinglePostResponse postResponse = new SinglePostResponse();
            UserResponse userResponse = new UserResponse();
            Timestamp timestamp = new Timestamp(posts.getTime().getTime()); //как-то коряво получилось с геттерами, не читаемо
            userResponse.setId(posts.getUserId());
            userResponse.setName(posts.getUsersId().getName());
            postResponse.setId(posts.getId());
            postResponse.setTimestamp(timestamp.getTime());
            postResponse.setTitle(posts.getText());
            postResponse.setAnnounce(posts.getText());
            postResponse.setLikeCount(3);     // не правильно
            postResponse.setDislikeCount(4);
            postResponse.setCommentCount(5);
            postResponse.setViewCount(posts.getViewCount());
            postResponse.setUserResponse(userResponse);
            singlePostResponses.add(postResponse);
        });
        return singlePostResponses;
    }

    private List<SinglePostResponse> getPostResponseListByUserQuery(String query) {
        List<SinglePostResponse> singlePostResponses = new ArrayList<>();
        postRepository.getPostsByUserQuery(query).forEach(posts -> {
            SinglePostResponse postResponse = new SinglePostResponse();
            UserResponse userResponse = new UserResponse();
            Timestamp timestamp = new Timestamp(posts.getTime().getTime());
            userResponse.setId(posts.getUserId());
            userResponse.setName(posts.getUsersId().getName());
            postResponse.setId(posts.getId());
            postResponse.setTimestamp(timestamp.getTime());
            postResponse.setTitle(posts.getText());
            postResponse.setAnnounce(posts.getText());
            postResponse.setLikeCount(3);     // не правильно
            postResponse.setDislikeCount(4);
            postResponse.setCommentCount(5);
            postResponse.setViewCount(posts.getViewCount());
            postResponse.setUserResponse(userResponse);
            singlePostResponses.add(postResponse);
        });
        return singlePostResponses;
    }

    private List<SinglePostResponse> getPostResponseForOneDay(Date date) {
        List<SinglePostResponse> singlePostResponses = new ArrayList<>();
        postRepository.getPostsOrderByDate().forEach(posts -> {
            SinglePostResponse postResponse = new SinglePostResponse();
            UserResponse userResponse = new UserResponse();
            Timestamp timestamp = new Timestamp(posts.getTime().getTime());

            if (posts.getTime().equals(date)) {
                userResponse.setId(posts.getUserId());
                userResponse.setName(posts.getUsersId().getName());
                postResponse.setId(posts.getId());
                postResponse.setTimestamp(timestamp.getTime());
                postResponse.setTitle(posts.getText());
                postResponse.setAnnounce(posts.getText());
                postResponse.setLikeCount(3);
                postResponse.setDislikeCount(4);
                postResponse.setCommentCount(5);
                postResponse.setViewCount(posts.getViewCount());
                postResponse.setUserResponse(userResponse);
                singlePostResponses.add(postResponse);
            }
        });
        return singlePostResponses;
    }

    private List<SinglePostResponse> getPostResponseListByTag(String tag) {
        List<SinglePostResponse> singlePostResponses = new ArrayList<>();
        postRepository.getPostsByTag(tag).forEach(posts -> {
            SinglePostResponse postResponse = new SinglePostResponse();
            UserResponse userResponse = new UserResponse();
            Timestamp timestamp = new Timestamp(posts.getTime().getTime());
            userResponse.setId(posts.getUserId());
            userResponse.setName(posts.getUsersId().getName());
            postResponse.setId(posts.getId());
            postResponse.setTimestamp(timestamp.getTime());
            postResponse.setTitle(posts.getText());
            postResponse.setAnnounce(posts.getText());
            postResponse.setLikeCount(3);     // не правильно
            postResponse.setDislikeCount(4);
            postResponse.setCommentCount(5);
            postResponse.setViewCount(posts.getViewCount());
            postResponse.setUserResponse(userResponse);
            singlePostResponses.add(postResponse);
        });
        return singlePostResponses;
    }

    public SinglePostResponse getPostById(Integer id) {
        Posts posts = postRepository.getPostsById(id);
        SinglePostResponse postResponse = new SinglePostResponse();
        UserResponse userResponse = new UserResponse();
        Timestamp timestamp = new Timestamp(posts.getTime().getTime());
        userResponse.setId(posts.getUserId());
        userResponse.setName(posts.getUsersId().getName());
        postResponse.setId(posts.getId());
        postResponse.setTimestamp(timestamp.getTime());
        postResponse.setTitle(posts.getText());
        postResponse.setAnnounce(posts.getText());
        postResponse.setLikeCount(3);
        postResponse.setDislikeCount(4);
        postResponse.setCommentCount(5);
        postResponse.setViewCount(posts.getViewCount());
        postResponse.setUserResponse(userResponse);

        return postResponse;
    }
}
