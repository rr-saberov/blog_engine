package ru.spring.app.engine.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum Permission {
    USER("user:write"),
    MODERATE("user:moderate");

    @Getter
    private final String permission;
}
