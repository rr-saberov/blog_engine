package ru.spring.app.engine.entity;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
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
    private int isModerator;
    @Column(nullable = false)
    private Date regTime;
    @Column(nullable = false)
    @Size(min = 4, max = 15)
//    @Pattern(regexp = "[^0-9\\p{all}]")
    private String name;
    @Column(nullable = false, columnDefinition = "text")
    @Pattern(regexp = ".+\\@.+\\.(com|ru)")
    private String email;
    @Column(nullable = false, length = 255)
    @Size(min = 8, max = 25)
//    @Pattern(regexp = "[^а-яА-Я]")
    private String password;
    private String code;
    @Column(columnDefinition = "text")
    private String photo;

    public Role getRole() {
        return isModerator == 1 ? Role.MODERATOR : Role.USER;
    }


    @OneToMany
    @JoinColumn(name = "user_posts", referencedColumnName = "id")
    private List<Post> postsList;

    @OneToMany
    @JoinColumn(name = "user_post_votes_id", referencedColumnName = "id")
    private List<PostVotes> postVotes;
}
