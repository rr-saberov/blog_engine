package ru.spring.app.engine.api.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import ru.spring.app.engine.api.response.errors.AddPostErrors;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AddPostResponse {
    private boolean result;
    private List<AddPostErrors> errors;
}
