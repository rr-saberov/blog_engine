package ru.spring.app.engine.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegistrationResponse {
    @JsonProperty("e_mail")
    private String email;
    private String password;
    private String name;
    private String captcha;
    private String captchaSecret;
}
