package ru.spring.app.engine.api.response.errors;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EditProfileErrors {
    private String email;
    private String photo;
    private String name;
    private String password;
}
