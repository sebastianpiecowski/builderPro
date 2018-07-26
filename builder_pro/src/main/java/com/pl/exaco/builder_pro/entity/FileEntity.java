package com.pl.exaco.builder_pro.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.pl.exaco.builder_pro.utils.datetimeParser;
import lombok.Data;

@Data
@Entity
@Table(name = "[File]")
public class FileEntity implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
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
    @Column(name = "DiawiUrl")
    private String diawiUrl;
    @Column(name = "ExpirationDate")
    private Timestamp expirationDate;
    @ManyToOne
    @JoinColumn(name = "StatusId")
    private StatusDictEntity statusId;

    public Timestamp getExpirationDate() {
        Timestamp parsed= datetimeParser.timestampParses(expirationDate);
        return parsed;
    }
}
