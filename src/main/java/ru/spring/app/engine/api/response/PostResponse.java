package ru.spring.app.engine.api.response;

import lombok.Data;
import ru.spring.app.engine.entity.Posts;
import ru.spring.app.engine.entity.Users;

import java.util.List;

@Data
public class PostResponse {

    private List<Posts> posts;
    private int id;
    private long timestamp;
    private Users user;
    private String title;
    private String announce;
    private int likeCount;
    private int dislikeCount;
    private int commentCount;
    private int viewCount;
}
