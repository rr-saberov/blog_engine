package ru.spring.app.engine.api.response;

import lombok.Data;

import java.util.List;

@Data
public class CalendarResponse {
    private List<PostInDayResponse> posts;
}
