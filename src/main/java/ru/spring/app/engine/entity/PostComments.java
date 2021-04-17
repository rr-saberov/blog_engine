package ru.spring.app.engine.entity;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@NoArgsConstructor
@Table(name = "post_comments")
@ApiModel(description = "data model of post comments")
public class PostComments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, columnDefinition = "serial")
    private int id;
    private int parentId;
    @Column(nullable = false)
    private int postId;
    @Column(nullable = false)
    private int userId;
    @Column(nullable = false)
    private Date time;
    @Column(nullable = false, columnDefinition = "text")
    private String text;

    @OneToOne(mappedBy = "postComments")
    private Post postsId;
    @OneToOne(mappedBy = "postsComments")
    private Users usersId;
}


