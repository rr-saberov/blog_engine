package ru.spring.app.engine.api.response;

import lombok.Data;

@Data
public class CommentResponse {
    private int id;
    private long timestamp;
    private String text;
}
