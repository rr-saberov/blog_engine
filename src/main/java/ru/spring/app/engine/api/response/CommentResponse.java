package ru.spring.app.engine.api.response;

import lombok.Data;

@Data
public class CommentResponse {
    private long id;
    private long timestamp;
    private String text;
    private UserResponse user;
}
