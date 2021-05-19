package ru.spring.app.engine.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class CurrentPostResponse {
    private int id;
    private long timestamp;
    private boolean isActive;
    @JsonProperty("user")
    private CommentUserResponse userResponse;
    private String title;
    private long likeCount;
    private long dislikeCount;
    private int viewCount;
    private List<CommentResponse> comments;
    private String[] tags;
}
