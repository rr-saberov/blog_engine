package ru.spring.app.engine.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ChangePasswordRequest {
    private String code;
    private String password;
    private String captcha;
    @JsonProperty("captchaSecret")
    private String captchaSecret;
}
