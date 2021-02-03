package ru.spring.app.engine.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Entity
@NoArgsConstructor
@Table(name = "post_comments")
public class PostComments {
    @Id
    private int id;
    private int parent_id;
    private int post_id;
    private int user_id;
    private Date time;
    private String text;

    public PostComments(int parent_id, int post_id, int user_id, Date time, String text) {
        this.parent_id = parent_id;
        this.post_id = post_id;
        this.user_id = user_id;
        this.time = time;
        this.text = text;
    }
}


