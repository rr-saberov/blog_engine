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
@Table(name = "users")
public class Users {

    @Id
    private int id;
    private boolean is_moderator;
    private Date reg_time;
    private String name;
    private String password;
    private String code;
    private String photo;

    public Users(boolean is_moderator, Date reg_time, String name, String password, String code, String photo) {
        this.is_moderator = is_moderator;
        this.reg_time = reg_time;
        this.name = name;
        this.password = password;
        this.code = code;
        this.photo = photo;
    }
}
