package ru.spring.app.engine.api.request;

import lombok.Data;

@Data
public class PostRequest {
    private long timestamp;
    private int isActive;
    private String title;
    private String[] tags;
    private String text;
}
