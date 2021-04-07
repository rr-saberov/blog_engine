package ru.spring.app.engine.api.response;

import lombok.Data;

import java.util.List;

@Data
public class PostsResponse {
    private int count;
    private List<SinglePostResponse> posts;
}
