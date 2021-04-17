package ru.spring.app.engine.api.response;

import lombok.Data;

import java.util.List;

@Data
public class PostsResponse {
    private Long count;
    private List<SinglePostResponse> posts;
}
