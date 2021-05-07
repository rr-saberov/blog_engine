package ru.spring.app.engine.entity;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity
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
    @JoinColumn(name = "moder_id", referencedColumnName = "id")
    private Users moderId;

    @ManyToOne
    @JoinColumn(name = "users_id")
    private Users usersId;

    @OneToMany
    @JoinColumn(name = "post_votes_id", referencedColumnName = "id")
    private List<PostVotes> postVotes;

}