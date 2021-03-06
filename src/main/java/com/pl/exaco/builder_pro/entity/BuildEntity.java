package com.pl.exaco.builder_pro.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

import java.io.Serializable;

@Data
@Entity
@Table(name = "Build")
public class BuildEntity implements Serializable {

    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    @Column(name="Id")
    private Integer id;
    @ManyToOne
    @JoinColumn(name="BuildDictId")
    private BuildDictEntity buildDictId;
    @ManyToOne
    @JoinColumn(name="ProjectId")
    private ProjectEntity projectId;
    @ManyToOne
    @JoinColumn(name="FlavorDictId")
    private FlavorDictEntity flavorDictId;

}
