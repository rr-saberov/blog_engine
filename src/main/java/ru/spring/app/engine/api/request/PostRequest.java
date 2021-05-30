package ru.spring.app.engine.api.request;

import lombok.Data;
import ru.spring.app.engine.entity.Tag;

import java.util.List;

@Data
public class PostRequest {
    private long timestamp;
    private int isActive;
    private String title;
    private List<Tag> tags;
    private String text;
}
