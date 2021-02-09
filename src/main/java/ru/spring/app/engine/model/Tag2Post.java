package ru.spring.app.engine.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@Table(name = "tag2post")
public class Tag2Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, columnDefinition = "serial")
    private int id;
    @Column(nullable = false)
    private int postId;
    @Column(nullable = false)
    private int tagId;

    @OneToOne
    @JoinColumn(name = "posts_id")
    private Posts postsId;

    @OneToOne
    @JoinColumn(name = "tags_id")
    private Tags tagsId;
}
