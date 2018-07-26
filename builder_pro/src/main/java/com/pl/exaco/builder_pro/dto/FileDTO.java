package com.pl.exaco.builder_pro.dto;

import com.pl.exaco.builder_pro.entity.BuildEntity;
import lombok.Data;

@Data
public class FileDTO {
    private Integer Id;
    private String fileName;
    private String uploadDate;
    private BuildEntity buildId;
    private String diawiUrl;
    private String expirationDate;
    private
}
