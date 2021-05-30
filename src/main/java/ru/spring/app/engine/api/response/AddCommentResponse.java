package ru.spring.app.engine.api.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import ru.spring.app.engine.api.response.errors.AddCommentErrors;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AddCommentResponse {
    private long id;
    private boolean result;
    private List<AddCommentErrors> errors;
}
