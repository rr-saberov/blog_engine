package ru.spring.app.engine.api.response;

import lombok.Data;

import java.util.List;

@Data
public class PostResponseById {
    private int id;
    private long timestamp;
    private boolean active;
    private UserResponse userResponse;
    private String title;
    private String text;
    private int likeCount;
    private int dislikeCount;
    private int viewCount;
    private List<CommentsResponse> comments;
    private List<TagsResponseForPost> tags;
}