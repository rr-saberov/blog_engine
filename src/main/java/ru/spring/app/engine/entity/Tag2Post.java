package ru.spring.app.engine.entity;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table
@ApiModel(description = "data model of tags to post")
public class Tag2Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, columnDefinition = "serial")
    private long id;

    @Column(name = "post_id", nullable = false)
    private long postId;

    @Column(name = "tag_id", nullable = false)
    private long tagId;

}
