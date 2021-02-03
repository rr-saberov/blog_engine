package ru.spring.app.engine.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Entity
@NoArgsConstructor
@Table(name = "post_votes")
public class PostVotes {
    @Id
    private int id;
    @Column
    private int user_id;
    @Column
    private int post_id;
    @Column
    private Date time;
    @Column
    private boolean value;

    public PostVotes(int user_id, int post_id, Date time, boolean value) {
        this.user_id = user_id;
        this.post_id = post_id;
        this.time = time;
        this.value = value;
    }
}
