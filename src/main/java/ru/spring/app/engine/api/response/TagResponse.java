package ru.spring.app.engine.api.response;

import lombok.Data;
import ru.spring.app.engine.entity.Tags;

import java.util.List;

@Data
public class TagResponse {
    private List<Tags> tags;
}
