package com.pl.exaco.builder_pro.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "StatusDict")
public class StatusDictEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Id")
    private Integer id;
    @Column(name = "Name")
    private String name;
}
