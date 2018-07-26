package com.pl.exaco.builder_pro.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class appNameParser {

    public static Map<String, String> parseApk(String name, StatusResponse status) {
        Map<String, String> applicationInfo=new HashMap<>();

        String[] arr=name.split("-");
        System.out.println(arr.length);

        applicationInfo.put("ProjectName", arr[0]);
        applicationInfo.put("Flavor", arr[1]);
        applicationInfo.put("BuildType", arr[2]);
        applicationInfo.put("FileName", name);
        applicationInfo.put("diawiUrl", status.getLink());

        System.out.println(name);
        return applicationInfo;
    }
}
