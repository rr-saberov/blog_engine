package ru.spring.app.engine.entity;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "global_settings")
@ApiModel(description = "data model of settings")
public class GlobalSettings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, columnDefinition = "serial")
    private int id;
    @Column(nullable = false)
    private String code;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String value;
}
