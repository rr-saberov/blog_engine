package ru.spring.app.engine.entity;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@Table(name = "users")
@ApiModel(description = "data model of users")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, columnDefinition = "serial")
    private int id;

    @Column(nullable = false)
    private boolean isModerator;

    @Column(nullable = false)
    private Date regTime;

    @Column(nullable = false)
    @Size(min = 4, max = 15)
    @Pattern(regexp = "^[0-9\\p{all}}]")
    private String name;

    @Column(nullable = false)
    @Pattern(regexp = ".+\\@.+\\.(com|ru)")
    private String email;

    @Column(nullable = false)
    @Size(min = 8, max = 25)
    @Pattern(regexp = "^[а-яА-Я]")
    private String password;

    private String code;

    @Column(columnDefinition = "text")
    private String photo;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "postsComments", referencedColumnName = "id")
    private PostComments postsComments;

    @OneToMany
    @JoinColumn(name = "user_posts", referencedColumnName = "id")
    private List<Posts> postsList = new ArrayList<>();

    @OneToMany
    @JoinColumn(name = "user_post_votes_id", referencedColumnName = "id")
    private List<PostVotes> postVotes = new ArrayList<>();
}
