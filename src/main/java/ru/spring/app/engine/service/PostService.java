package ru.spring.app.engine.service;

import org.springframework.stereotype.Service;
import ru.spring.app.engine.api.response.PostResponse;

@Service
public class PostService {
    
    public PostResponse getPosts() {
        PostResponse postResponse = new PostResponse();
        postResponse.setAnnounce("test");
        postResponse.setCommentCount(5);
        return postResponse;
    }
}
