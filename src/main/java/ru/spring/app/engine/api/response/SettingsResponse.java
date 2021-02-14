package ru.spring.app.engine.api.response;

import lombok.Data;

@Data
public class SettingsResponse {

    private boolean multiuserMode;
    private boolean postPremoderation;
    private boolean statisticsIsPublic;
}

