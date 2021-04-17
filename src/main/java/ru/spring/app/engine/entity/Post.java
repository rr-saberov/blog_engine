package ru.spring.app.engine.entity;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@NoArgsConstructor
@Table(name = "posts")
@ApiModel(description = "data model of posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, columnDefinition = "serial")
    private int id;
    @Column(nullable = false)
    private int isActive;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ModerationStatus moderationStatus;
    private int moderatorId;
    @Column(nullable = false)
    private int userId;
    @Column(nullable = false)
    private Date time;
    @Column(nullable = false, columnDefinition = "text")
    private String text;
    @Column(nullable = false)
    private int viewCount;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "post_comments_id", referencedColumnName = "id")
    private PostComments postComments;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "moder_id", referencedColumnName = "id")
    private Users moderId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "posts_votes_id", referencedColumnName = "id")
    private PostVotes postVotes;

    @OneToOne(mappedBy = "postsId")
    private Tag2Post tag2Post;

    @ManyToOne
    @JoinColumn(name = "users_id")
    private Users usersId;
}