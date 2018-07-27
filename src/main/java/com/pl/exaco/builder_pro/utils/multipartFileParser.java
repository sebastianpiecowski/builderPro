package com.pl.exaco.builder_pro.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class multipartFileParser {

    public static final String DIRECTORY_PATH="storage/";

    public static File parseMultipartFileToFile(MultipartFile file) {
        new File(DIRECTORY_PATH).mkdir();
        File newFile = null;
        FileOutputStream fos;
        try {
            newFile = new File(DIRECTORY_PATH + file.getOriginalFilename());
            fos = new FileOutputStream(newFile);
            fos.write(file.getBytes());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return newFile;
    }
}
