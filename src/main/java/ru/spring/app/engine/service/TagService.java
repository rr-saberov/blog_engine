package ru.spring.app.engine.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.spring.app.engine.api.response.SingleTagResponse;
import ru.spring.app.engine.api.response.TagsResponse;
import ru.spring.app.engine.repository.TagRepository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TagService {

    private final TagRepository tagRepository;

    @Autowired
    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public TagsResponse getTags(String query) {
        List<SingleTagResponse> singleTagResponses = new ArrayList<>();
        tagRepository.getTagsOrderByPopularity().forEach(tags -> {
                SingleTagResponse tagResponse = new SingleTagResponse();
                if (tags.getName().startsWith(query)) {
                    tagResponse.setName(tags.getName());
                    tagResponse.setWeight(getTagWeight(tags.getName()));
                }
                singleTagResponses.add(tagResponse);
            });

        List<SingleTagResponse> responses = singleTagResponses.stream()
                .sorted(Comparator.comparing(SingleTagResponse::getWeight).reversed())
                .collect(Collectors.toList());

        return new TagsResponse(responses);
    }

    private Double getTagWeight(String tagName) {
        return (double) tagRepository.getPostsCountWithTag(tagName) / tagRepository.getPostsCount();
    }
}
