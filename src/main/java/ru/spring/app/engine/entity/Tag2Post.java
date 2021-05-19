package ru.spring.app.engine.entity;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table(name = "tag2post")
@ApiModel(description = "data model of tags to post")
public class Tag2Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, columnDefinition = "serial")
    private int id;
    @Column(nullable = false)
    private int postId;
    @Column(nullable = false)
    private int tagId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "tags_id", referencedColumnName = "id")
    private Tags tagsId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "posts_id", referencedColumnName = "id")
    private Post posts;

}
