package ru.spring.app.engine.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@NoArgsConstructor
@Table(name = "posts")
public class Posts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, columnDefinition = "serial")
    private int id;
    @Column(nullable = false)
    private boolean isActive;
    @Column(nullable = false)
    private Enum moderationStatus;
    private int moderatorId;
    @Column(nullable = false)
    private int userId;
    @Column(nullable = false)
    private Date date;
    @Column(nullable = false, columnDefinition = "text")
    private String text;
    @Column(nullable = false)
    private int viewCount;

}
