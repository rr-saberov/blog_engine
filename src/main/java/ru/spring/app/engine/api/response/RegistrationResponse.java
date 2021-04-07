package ru.spring.app.engine.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class RegistrationResponse {
    @JsonProperty("e_mail")
    private String email;
    private String password;
    private String name;
    private String captcha;
    private String captchaSecret;
}
