package ru.spring.app.engine.api.response;

import lombok.Data;
import ru.spring.app.engine.dto.User;

@Data
public class PostResponse {

    private int id;
    private long timestamp;
    private User user;
    private String title;
    private String announce;
    private int likeCount;
    private int dislikeCount;
    private int commentCount;
    private int viewCount;
}
