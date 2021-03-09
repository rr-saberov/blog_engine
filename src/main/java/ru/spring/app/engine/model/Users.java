package ru.spring.app.engine.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@Entity
@NoArgsConstructor
@Table(name = "users")
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

}
