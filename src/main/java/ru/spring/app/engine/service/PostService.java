package ru.spring.app.engine.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.spring.app.engine.api.request.PostRequest;
import ru.spring.app.engine.api.response.*;
import ru.spring.app.engine.entity.Post;
import ru.spring.app.engine.entity.Tag;
import ru.spring.app.engine.entity.User;
import ru.spring.app.engine.entity.enums.ModerationStatus;
import ru.spring.app.engine.exceptions.AccessIsDeniedException;
import ru.spring.app.engine.exceptions.PostNotFoundException;
import ru.spring.app.engine.repository.PostRepository;
import ru.spring.app.engine.repository.PostVotesRepository;
import ru.spring.app.engine.repository.TagRepository;
import ru.spring.app.engine.repository.UserRepository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final TagRepository tagRepository;
    private final PostVotesRepository postVotesRepository;

    public PostService(PostRepository postRepository, UserRepository userRepository, TagRepository tagRepository, PostVotesRepository postVotesRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.tagRepository = tagRepository;
        this.postVotesRepository = postVotesRepository;
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
        if (year == null) {
            calendarResponse = convertMapToResponse();
        } else {
            calendarResponse = convertMapToResponse(year);
        }
        return calendarResponse;
    }

    public CurrentPostResponse getPostById(Long id) throws PostNotFoundException {
        if (postRepository.findById(id).isPresent()) {
            postRepository.updatePostInfo(postRepository.getPostsById(id).getViewCount() + 1, id);
            return convertPostToCurrentPostResponse(postRepository.getPostsById(id));
        } else  {
            throw new PostNotFoundException("post with id: " + id + " not found");
        }
    }

    public AddPostResponse addNewPost(PostRequest request) {
        savePostFromRequest(request);
        AddPostResponse response = new AddPostResponse();
        return response;
    }

    public AddPostResponse updatePost(Long id, PostRequest request) {
        savePostFromRequest(request, id);
        AddPostResponse response = new AddPostResponse();
        return response;
    }

    public StatisticsResponse getStatistics(String email) throws AccessIsDeniedException {
        StatisticsResponse response = new StatisticsResponse();
        if (userRepository.findByEmail(email).get().getIsModerator() == 1) {
            response.setPostsCount(postRepository.findAll().size());
            response.setViewCount(postRepository.getTotalViewCount());
            response.setLikesCount(postRepository.getTotalLikesCount());
            response.setDislikesCount(postRepository.getTotalDislikesCount());
            response.setFirstPublication(postRepository.getPostsOrderByTime().get(0).getTime().getTime());
            return response;
        } else {
            throw new AccessIsDeniedException("access to statistic is denied");
        }
    }

    public Boolean addLike(Long postId, String email) {
        User currentUser = userRepository.findByEmail(email).get();
        if (postVotesRepository.findByUserId(currentUser.getId()).getValue() == - 1) {
            postVotesRepository.changeDislikeToLike(currentUser.getId());
            return true;
        }
        else if (postVotesRepository.findByUserId(currentUser.getId()).getValue() == 1) {
            return false;
        }
        else {
            postVotesRepository.addLike(postId, new Date(System.currentTimeMillis()), currentUser.getId());
            return true;
        }
    }

    public Boolean addDislike(Long postId, String email) {
        User currentUser = userRepository.findByEmail(email).get();
        if (postVotesRepository.findByUserId(currentUser.getId()).getValue() == - 1) {
            return false;
        }
        else if (postVotesRepository.findByUserId(currentUser.getId()).getValue() == 1) {
            postVotesRepository.changeLikeToDislike(currentUser.getId());
            return true;
        }
        else {
            postVotesRepository.addDislike(postId, new Date(System.currentTimeMillis()), currentUser.getId());
            return true;
        }
    }

    public Boolean moderatePost(Long id, String decision) {
        if (decision.equals("accept")) {
            postRepository.updatePostStatus("ACCEPTED", id);
            return true;
        } else if (decision.equals("decline")) {
            postRepository.updatePostStatus("DECLINED", id);
            return true;
        } else {
            return false;
        }
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
        userResponse.setName(postRepository.getNameFromPost(post.getUserId()));
        postResponse.setId(post.getId());
        postResponse.setTimestamp(timestamp.getTime() / 1_000);
        postResponse.setTitle(post.getText());
        postResponse.setAnnounce(post.getText().substring(Math.min(0, 25)));
        postResponse.setLikeCount(postRepository.getVotesForPost(post.getId())
                .stream().filter(vote -> vote.getValue() == 1).count());
        postResponse.setDislikeCount(postRepository.getVotesForPost(post.getId())
                .stream().filter(vote -> vote.getValue() == - 1).count());
        postResponse.setCommentCount(5);
        postResponse.setViewCount(post.getViewCount());
        postResponse.setUserResponse(userResponse);
        return postResponse;
    }

    private CurrentPostResponse convertPostToCurrentPostResponse(Post post) {
        CurrentPostResponse postResponse = new CurrentPostResponse();
        CommentUserResponse userResponse = new CommentUserResponse();
        String[] tags = new String[post.getTags().size()];
        List<String> tagNameList = post.getTags().stream().map(Tag::getName).collect(Collectors.toList());
        tags = tagNameList.toArray(tags);
        userResponse.setId(post.getUserId());
        userResponse.setName(postRepository.getNameFromPost(post.getUserId()));
        postResponse.setId(post.getId());
        postResponse.setTimestamp(new Timestamp(post.getTime().getTime()).getTime() / 1_000);
        postResponse.setTitle(post.getText());
        postResponse.setLikeCount(postRepository.getVotesForPost(post.getId())
                .stream().filter(vote -> vote.getValue() == 1).count());
        postResponse.setDislikeCount(postRepository.getVotesForPost(post.getId())
                .stream().filter(vote -> vote.getValue() == - 1).count());
        postResponse.setViewCount(post.getViewCount());
        postResponse.setUserResponse(userResponse);
        postResponse.setActive(post.getIsActive() == 1);
        postResponse.setTags(tags);
        postResponse.setComments(convertPostCommentsToResponse(post.getId()));
        return postResponse;
    }

    private List<CommentResponse> convertPostCommentsToResponse(Long postId) {
        List<CommentResponse> responses = new ArrayList<>();
        postRepository.getCommentsForPost(postId).forEach(postComments -> {
            User currentUser = userRepository.getUsersById(postComments.getUserId());
            CommentResponse commentResponse = new CommentResponse();
            commentResponse.setId(postComments.getId());
            commentResponse.setText(postComments.getText());
            commentResponse.setTimestamp(postComments.getTime().getTime());
            commentResponse.setUser(new UserResponse(currentUser.getId(), currentUser.getName(), currentUser.getPhoto()));
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

    private void savePostFromRequest(PostRequest request) {
        Post post = new Post();
        convertPostRequestToPost(request, post);
    }

    private void savePostFromRequest(PostRequest request, Long id) {
        Post post = new Post();
        post.setId(id);
        convertPostRequestToPost(request, post);
    }

    private void convertPostRequestToPost(PostRequest request, Post post) {
        List<Tag> tags = new ArrayList<>();
        Arrays.stream(request.getTags()).forEach((tag) -> {
            Tag currentTag = tagRepository.getTagByName(tag);
            tags.add(currentTag);
        });
        post.setIsActive(request.getIsActive());
        post.setText(request.getText());
        post.setTags(tags);
        post.setModerationStatus(ModerationStatus.NEW);
        if (new Date(request.getTimestamp()).after(new Date())) {
            post.setTime(new Date(request.getTimestamp()));
        } else {
            post.setTime(new Date());
        }
        postRepository.save(post);
    }
}