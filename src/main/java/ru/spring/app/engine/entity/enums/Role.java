package ru.spring.app.engine.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import ru.spring.app.engine.entity.enums.Permission;

import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
public enum Role {
    USER(Set.of(Permission.USER)),
    MODERATOR(Set.of(Permission.USER, Permission.MODERATE));

    @Getter
    private final Set<Permission> permissions;

    public Set<SimpleGrantedAuthority> getAuthorities() {
        return permissions.stream()
                .map(p -> new SimpleGrantedAuthority(p.getPermission()))
                .collect(Collectors.toSet());
    }
}
