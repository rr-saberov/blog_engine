package ru.spring.app.engine.api.response;

import lombok.Data;

@Data
public class StatisticsResponse {
    private long postsCount;
    private long likesCount;
    private long dislikesCount;
    private long viewCount;
    private long firstPublication;
}
