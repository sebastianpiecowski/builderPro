package com.pl.exaco.builder_pro.dto;

import com.pl.exaco.builder_pro.entity.BuildEntity;
import com.pl.exaco.builder_pro.entity.StatusDictEntity;
import com.pl.exaco.builder_pro.utils.datetimeParser;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class FileDTO {
    private Integer Id;
    private String fileName;
    private String uploadDate;
    private BuildEntity buildId;
    private String diawiUrl;
    private String expirationDate;
    private StatusDictEntity statusId;

    public void setUploadDate(Timestamp uploadDate){
        this.uploadDate= datetimeParser.parseToString(uploadDate);
    }

    public void setExpirationDate(Timestamp expirationDate) {
        this.expirationDate=datetimeParser.parseToString(expirationDate);
    }
}
