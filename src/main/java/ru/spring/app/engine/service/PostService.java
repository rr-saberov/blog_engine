package ru.spring.app.engine.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.spring.app.engine.api.response.PostsResponse;
import ru.spring.app.engine.api.response.SinglePostResponse;
import ru.spring.app.engine.api.response.UserResponse;
import ru.spring.app.engine.repository.PostRepository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
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

//    public Page<PostsResponse> getPosts(Integer offset, Integer limit, String mode) {
//        Pageable nextPage = PageRequest.of(offset, limit);
//        switch (mode) {
//            case "popular":
//                return new PageImpl<>(postRepository.getPostsByCommentCount(nextPage));
//            case "best":
//                return new PageImpl<>(postRepository.getPostsByLikes(nextPage));
//            case "early":
//                return new PageImpl<>(postRepository.getOldPosts(nextPage));
//            default:
//                return new PageImpl<>(postRepository.getPostsByDate(nextPage));
//        }
//    }


    public PostsResponse getPosts(Integer offset, Integer limit, String mode) {
        Pageable nextPage = PageRequest.of(offset, limit);
        switch (mode) {
/*            case "popular":
                return ResponseEntity.ok(new PageImpl<PostsResponse>(postsByCommentCount()));*/  //как реализовать пагинацию?
            case "best":
                return postsByLikeCount();
            case "early":
                return oldPostsByDate();
            default:
                return postsByDate();
        }
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

    private List<SinglePostResponse> getPostResponseList() {
        List<SinglePostResponse> singlePostResponses = new ArrayList<>();
        postRepository.getPostsByDate().forEach(posts -> {
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
/*
    public Integer getPostsCountInYear(Integer year) {
        return postRepository.postCountByYear(year);
    }*/

/*    public Page<Posts> getPostsByDate(Integer offset, Integer limit, Date date) {
        return postRepository.findPostsByDate(PageRequest.of(offset, limit), date);
    }*/

/*    public Page<Posts> getPostsByTag(Integer offset, Integer limit, Integer tagId) {
        return postRepository.findPostsByTag(PageRequest.of(offset, limit), tagId);
    }

    public Posts getPostById(Integer id) {
        return postRepository.getOne(id);
    }

    public ResponseEntity<PostsResponse> search(Integer offset, Integer limit, String query) {
        return ResponseEntity.ok(getAllPosts());        //запрос к репозиторию и через пагинацию(PageRequest) собрать в ответ формата PostResponse
    }*/
}
