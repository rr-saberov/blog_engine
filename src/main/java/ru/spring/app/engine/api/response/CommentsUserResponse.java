package ru.spring.app.engine.api.response;

import lombok.Data;

@Data
public class CommentsUserResponse {
    private int id;
    private String name;
    private String photo;
}
