package com.pl.exaco.builder_pro.utils;

import java.util.HashMap;
import java.util.Map;

public class appNameParser {

    public static Map<String, String> parseApk(String name, StatusResponse status) {
        Map<String, String> applicationInfo=new HashMap<>();

        String[] arr=name.split("-");

        applicationInfo.put("ProjectName", arr[0].toLowerCase());
        applicationInfo.put("Flavor", arr[1].toLowerCase());



//        System.out.println(arr2[0]);
        applicationInfo.put("BuildType", arr[2].split("\\.")[0].toLowerCase());

        applicationInfo.put("FileName", name);
        applicationInfo.put("DiawiUrl", status.getLink());

        return applicationInfo;
    }
}
