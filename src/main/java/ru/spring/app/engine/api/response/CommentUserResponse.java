package ru.spring.app.engine.api.response;

import lombok.Data;

@Data
public class CommentUserResponse {
    private long id;
    private String name;
    private String photo;
}
