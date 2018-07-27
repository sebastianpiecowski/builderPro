package com.pl.exaco.builder_pro.dto;

import com.pl.exaco.builder_pro.entity.FileEntity;
import com.pl.exaco.builder_pro.utils.datetimeParser;
import lombok.Data;

@Data
public class FileDTO {
    private Integer id;
    private String fileName;
    private String projectName;
    private Long uploadTimestamp;
    private String uploadDate;
    private DiawiDataDTO diawiData;
    private String statusName;

    public FileDTO(FileEntity fileEntity){
        id=fileEntity.getId();
        fileName=fileEntity.getFileName();
        projectName=fileEntity.getBuildId().getProjectId().getName();
        uploadTimestamp=fileEntity.getUploadDate().getTime();
        uploadDate=datetimeParser.parseToString(fileEntity.getUploadDate());
        diawiData=new DiawiDataDTO();
        diawiData.setUrl(fileEntity.getDiawiUrl());
        diawiData.setExpirationDate(fileEntity.getExpirationDate());
        diawiData.setExpirationTimestamp(fileEntity.getExpirationDate().getTime());
        try {
            statusName = fileEntity.getStatusId().getName();
        }
        catch (NullPointerException ne) {
            //opt
        }
    }
}

