package ru.spring.app.engine.api.response;

import lombok.Data;

@Data
public class CommentsResponse {
    private int id;
    private long timestamp;
    private String text;
    private CommentsUserResponse userResponse;
}
