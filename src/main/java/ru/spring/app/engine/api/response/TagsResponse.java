package ru.spring.app.engine.api.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class TagsResponse {
    private List<SingleTagResponse> tags;
}
