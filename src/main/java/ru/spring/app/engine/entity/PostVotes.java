package ru.spring.app.engine.entity;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "post_votes")
@ApiModel(description = "data model of votes")
public class PostVotes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, columnDefinition = "serial")
    private long id;

    @Column(nullable = false)
    private long userId;

    @Column(nullable = false)
    private long postId;

    @Column(nullable = false)
    private Date time;

    @Column(nullable = false)
    private int value;

}
