package ru.spring.app.engine.api.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RegistrationResponse {
    private boolean result;
    private List<String> errors;
}
