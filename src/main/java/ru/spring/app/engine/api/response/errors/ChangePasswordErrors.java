package ru.spring.app.engine.api.response.errors;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChangePasswordErrors {
    private String code;
    private String password;
    private String captcha;
}
