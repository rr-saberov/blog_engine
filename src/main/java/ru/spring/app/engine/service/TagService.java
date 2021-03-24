package ru.spring.app.engine.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.spring.app.engine.api.response.TagResponse;
import ru.spring.app.engine.entity.Tags;
import ru.spring.app.engine.repository.TagsRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TagService {

    private TagsRepository tagsRepository;

    @Autowired
    public TagService(TagsRepository tagsRepository) {
        this.tagsRepository = tagsRepository;
    }

    public TagResponse getTags() {
        TagResponse tagResponse = new TagResponse();
        return tagResponse;
    }

    public Map<Integer, String> getPostsByTag(Tags tag) {
        return tagsRepository.findAll().stream().filter(tags -> tags.getName().equals(tag.getName()))
                .collect(Collectors.toMap(Tags::getId, Tags::getName));
    }
/*        Map<Integer, Tags> postsByTag = new HashMap<>();
        List<Tags> tagsList = tagsRepository.findAll();
        for (Tags tags : tagsList) {
            if (tags.getName().equals(tag.getName())) {
                postsByTag.put(tags.getId(), tag);
            }
        }
        return postsByTag;
    }*/
}
