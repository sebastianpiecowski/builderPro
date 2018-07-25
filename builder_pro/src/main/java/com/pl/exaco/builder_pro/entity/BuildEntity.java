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
    @Column(name="id")
    private Integer id;
    @Column(name="build_type")
    private String buildType;
    @ManyToOne
    @JoinColumn(name="project_id")
    private ProjectEntity projectId;

}
