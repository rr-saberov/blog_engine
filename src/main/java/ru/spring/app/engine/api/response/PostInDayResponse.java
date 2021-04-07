package ru.spring.app.engine.api.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PostInDayResponse {
    private Integer year;
    private Long count;
}
