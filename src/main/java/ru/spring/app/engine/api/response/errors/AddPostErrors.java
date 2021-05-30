package ru.spring.app.engine.api.response.errors;

import lombok.Data;

@Data
public class AddPostErrors {
    private String title;
    private String text;
}
