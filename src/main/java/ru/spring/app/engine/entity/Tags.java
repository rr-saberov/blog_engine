package ru.spring.app.engine.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@Table(name = "tags")
@ApiModel(description = "data model of tags")
public class Tags {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, columnDefinition = "serial")
    @JsonIgnore
    private int id;

    @Column(nullable = false)
    private String name;

    @JsonIgnore
    @OneToOne(mappedBy = "tagsId")
    private Tag2Post tag2Post;

}
