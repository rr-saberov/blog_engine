package ru.spring.app.engine.api.response;

import lombok.Data;
import ru.spring.app.engine.api.response.errors.ChangePasswordErrors;

import java.util.List;

@Data
public class ChangePasswordResponse {
    private boolean result;
    private List<ChangePasswordErrors> errors;
}
