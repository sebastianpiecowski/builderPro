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

@Data
@Entity
@Table(name = "Build")
public class BuildEntity {

    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    @Column(name="Id")
    private Integer id;
    @Column(name="BuildType")
    private String buildType;
    @ManyToOne
    @JoinColumn(name="ProjectId")
    private ProjectEntity projectId;

}
