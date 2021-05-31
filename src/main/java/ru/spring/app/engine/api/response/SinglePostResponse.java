package ru.spring.app.engine.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SinglePostResponse {
    private long id;
    private long timestamp;
    @JsonProperty("user")
    private UserResponse userResponse;
    private String title;
    private String announce;
    private long likeCount;
    private long dislikeCount;
    private int commentCount;
    private int viewCount;
}
