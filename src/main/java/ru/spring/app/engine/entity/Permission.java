package ru.spring.app.engine.entity;

import lombok.Getter;

public enum Permission {
    USER("user:write"),
    MODERATE("user:moderate");

    @Getter
    private final String permission;

    Permission(String permission) {
        this.permission = permission;
    }
}
