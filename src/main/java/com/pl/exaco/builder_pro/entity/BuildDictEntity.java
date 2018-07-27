package com.pl.exaco.builder_pro.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "BuildDict")
public class BuildDictEntity implements Serializable {

    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    @Column(name="Id")
    private Integer id;
    @Column(name="Name")
    private String name;

}
