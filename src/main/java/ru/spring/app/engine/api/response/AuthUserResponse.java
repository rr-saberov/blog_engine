package ru.spring.app.engine.api.response;

import lombok.Data;

@Data
public class AuthUserResponse {
    private long id;
    private String name;
    private String photo;
    private String email;
    private boolean moderation;
    private int moderationCount;
    private boolean settings;
}
