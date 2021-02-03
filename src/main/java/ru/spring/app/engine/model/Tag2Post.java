package ru.spring.app.engine.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@NoArgsConstructor
@Table(name = "tag2post")
public class Tag2Post {
    @Id
    private int id;
    @Column
    private int post_id;
    @Column
    private int tag_id;

    public Tag2Post(int post_id, int tag_id) {
        this.post_id = post_id;
        this.tag_id = tag_id;
    }
}
