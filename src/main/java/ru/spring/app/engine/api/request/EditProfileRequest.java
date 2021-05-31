package ru.spring.app.engine.api.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EditProfileRequest {
    private String name;
    private String email;
    private String password;
}
