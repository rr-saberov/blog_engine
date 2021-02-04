package ru.spring.app.engine.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@NoArgsConstructor
@Table(name = "post_votes")
public class PostVotes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, columnDefinition = "serial")
    private int id;
    @Column(nullable = false)
    private int userId;
    @Column(nullable = false)
    private int postId;
    @Column(nullable = false)
    private Date time;
    @Column(nullable = false)
    private boolean value;


}
