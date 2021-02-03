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
@Table(name = "posts")
public class Posts {
    @Id
    private int id;
    private boolean is_active;
    private String moderation_status;
    private int moderator_id;
    private int user_id;
    private Date date;
    private String text;
    private int view_count;

    public Posts(boolean is_active, String moderation_status, int moderator_id, int user_id, Date date, String text, int view_count) {
        this.is_active = is_active;
        this.moderation_status = moderation_status;
        this.moderator_id = moderator_id;
        this.user_id = user_id;
        this.date = date;
        this.text = text;
        this.view_count = view_count;
    }
}
