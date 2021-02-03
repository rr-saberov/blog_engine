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
@Table(name = "captcha_codes")
public class CaptchaCodes {
    @Id
    private int id;
    private Date time;
    private boolean code;
    private boolean secret_code;

    public CaptchaCodes(Date time, boolean code, boolean secret_code) {
        this.time = time;
        this.code = code;
        this.secret_code = secret_code;
    }
}
