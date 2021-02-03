package ru.spring.app.engine.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@NoArgsConstructor
@Table(name = "tags")
public class Tags {
    @Id
    private int id;
    private String name;

    public Tags(String name) {
        this.name = name;
    }
}
