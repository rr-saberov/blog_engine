package ru.spring.app.engine.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.spring.app.engine.api.response.SingleTagResponse;
import ru.spring.app.engine.api.response.TagsResponse;
import ru.spring.app.engine.entity.Tags;
import ru.spring.app.engine.repository.TagRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TagService {

    private TagRepository tagRepository;

    @Autowired
    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public TagsResponse getTags() {
        List<SingleTagResponse> singleTagResponses = new ArrayList<>();
        tagRepository.getTagsOrderByName().forEach(tags -> {
                SingleTagResponse tagResponse = new SingleTagResponse();
                tagResponse.setName(tags.getName());
                tagResponse.setWeight(0.5);
                singleTagResponses.add(tagResponse);
            });
        return new TagsResponse(singleTagResponses);
    }

    public TagsResponse getTags(String query) {
        List<SingleTagResponse> singleTagResponses = new ArrayList<>();
        tagRepository.getTagsOrderByName().forEach(tags -> {
                SingleTagResponse tagResponse = new SingleTagResponse();
                if (tags.getName().startsWith(query)) {
                    tagResponse.setName(tags.getName());
                    tagResponse.setWeight(0.5);
                }
                singleTagResponses.add(tagResponse);
            });
        return new TagsResponse(singleTagResponses);
    }


    public Map<Integer, String> getPostsByTag(Tags tag) {
        return tagRepository.findAll().stream().filter(tags -> tags.getName().equals(tag.getName()))
                .collect(Collectors.toMap(Tags::getId, Tags::getName));
    }
}
