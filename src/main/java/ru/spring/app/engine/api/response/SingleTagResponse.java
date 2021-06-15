package ru.spring.app.engine.api.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SingleTagResponse {
    private String name;
    private double weight;
}
