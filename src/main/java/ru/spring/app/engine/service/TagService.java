package ru.spring.app.engine.service;

import org.springframework.stereotype.Service;
import ru.spring.app.engine.api.response.TagResponse;
import ru.spring.app.engine.entity.Tags;
import ru.spring.app.engine.repository.TagsRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TagService {

    TagsRepository tagsRepository;

    public TagResponse getTags() {
        TagResponse tagResponse = new TagResponse();
        return tagResponse;
    }

    public Map<Integer, Tags> getPostsByTag(Tags tag) {
        Map<Integer, Tags> postsByTag = new HashMap<>();
        List<Tags> tagsList = tagsRepository.findAll();
        for (Tags tags : tagsList) {
            if (tags.getName().equals(tag.getName())) {
                postsByTag.put(tags.getId(), tag);
            }
        }
        return postsByTag;
    }
}
