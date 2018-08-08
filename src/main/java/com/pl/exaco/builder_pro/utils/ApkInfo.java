package com.pl.exaco.builder_pro.utils;

import lombok.Data;

@Data
public class ApkInfo {

    private String projectName;
    private String fileName;
    private String flavor;
    private String buildType;
    private String diawiUrl;

    public ApkInfo(String fileName) {
        String[] infoArray = fileName.split("-");
        if(infoArray.length < 3) {
            throw new IllegalArgumentException("FileName does not meet patter requirements");
        }
        this.projectName = infoArray[0];
        this.flavor = infoArray[1].toLowerCase();
        this.buildType = infoArray[2].toLowerCase();
        this.fileName = fileName;
    }
}
