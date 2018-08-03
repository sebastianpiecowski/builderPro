package com.pl.exaco.builder_pro.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileAdapter {

    public static File parseMultipartFileToFile(MultipartFile file) {
        //TODO output stream does not close if exception is thrown (add finally block)
        //TODO mkdir may throw exception as well (put in try-catch clause)
        new File(Configuration.DIRECTORY_PATH).mkdir();
        File newFile = null;
        FileOutputStream fos;
        try {
            newFile = new File(Configuration.DIRECTORY_PATH + file.getOriginalFilename());
            fos = new FileOutputStream(newFile);
            fos.write(file.getBytes());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return newFile;
    }

    public static boolean deleteFile(String filePath) {
        try{
            File file = new File(Configuration.DIRECTORY_PATH+filePath);

            if(file.delete()){
                System.out.println(file.getName() + " is deleted!");
                return true;
            }else{
                System.out.println("Delete operation is failed.");
                return false;
            }

        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
