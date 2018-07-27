package com.pl.exaco.builder_pro.utils;

import com.pl.exaco.builder_pro.utils.diawi.StatusResponse;

import java.util.HashMap;
import java.util.Map;

public class appNameParser {


    public static final String PROJECT_NAME = "ProjectName";
    public static final String FLAVOR = "Flavor";
    public static final String BUILD_TYPE = "BuildType";
    public static final String FILE_NAME = "FileName";
    public static final String DIAWI_URL = "DiawiUrl";


    public static Map<String, String> parseApk(String fileName, StatusResponse status) {
        Map<String, String> applicationInfo = new HashMap<>();

        String[] arr = fileName.split("-");
        applicationInfo.put(PROJECT_NAME, arr[0]);
        applicationInfo.put(FLAVOR, arr[1].toLowerCase());
        applicationInfo.put(BUILD_TYPE, arr[2].toLowerCase());
        applicationInfo.put(FILE_NAME, fileName);
        applicationInfo.put(DIAWI_URL, status.getLink());

        return applicationInfo;
    }
}
