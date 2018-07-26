package com.pl.exaco.builder_pro.entity;

import java.sql.Timestamp;

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
@Table(name = "File")
public class FileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Id")
    private Integer id;
    @Column(name = "FileName")
    private String fileName;
    @Column(name= "UploadDate")
    private Timestamp uploadDate;
    @ManyToOne
    @JoinColumn(name = "BuildId")
    private BuildEntity buildId;

}
