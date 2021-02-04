package ru.spring.app.engine.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
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
    private String name;
    @Column(nullable = false)
    private String password;
    private String code;
    @Column(columnDefinition = "text")
    private String photo;

}
