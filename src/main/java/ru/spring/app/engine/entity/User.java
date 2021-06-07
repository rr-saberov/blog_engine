package ru.spring.app.engine.entity;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.spring.app.engine.entity.enums.Role;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@Entity
@NoArgsConstructor
@Table(name = "users")
@ApiModel(description = "data model of users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, columnDefinition = "serial")
    private long id;

    @Column(nullable = false)
    private int isModerator;

    @Column(nullable = false)
    private Date regTime;

    @Column(nullable = false)
    @Size(min = 4, max = 25)
//    @Pattern(regexp = "[^0-9\\p{all}]")
    private String name;

    @Column(nullable = false, columnDefinition = "text")
    @Pattern(regexp = ".+\\@.+\\.(com|ru)")
    private String email;

    @Column(nullable = false, columnDefinition = "text")
/*    @Size(min = 6, max = 25)*/
//    @Pattern(regexp = "[^а-яА-Я]")
    private String password;

    private String code;

    @Column(columnDefinition = "text")
    private String photo;

    public Role getRole() {
        return isModerator == 1 ? Role.MODERATOR : Role.USER;
    }
}
