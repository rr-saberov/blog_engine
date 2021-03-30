package ru.spring.app.engine.api.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import ru.spring.app.engine.entity.Posts;
import ru.spring.app.engine.repository.PostRepository;

import java.util.List;

@Data
public class PostResponse {

    @JsonIgnore
    private PostRepository postRepository;
    private int count;
    private List<Posts> posts;

    public PostResponse() {
        this.posts = postRepository.findAll();
        this.count = posts.size();
    }
//    private int id;
//    private long timestamp;
//    private Users user;
//    private String title;
//    private String announce;
//    private int likeCount;
//    private int dislikeCount;
//    private int commentCount;
//    private int viewCount;
}
