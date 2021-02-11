package ru.spring.app.engine.service;

import org.springframework.stereotype.Service;
import ru.spring.app.engine.api.response.TagResponse;

@Service
public class TagService {

    public TagResponse getTags() {
        TagResponse tagResponse = new TagResponse();
        return tagResponse;
    }
}
