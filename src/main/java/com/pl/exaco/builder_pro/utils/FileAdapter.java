package com.pl.exaco.builder_pro.utils;

import com.pl.exaco.builder_pro.dto.StorageInfoDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

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

    public static StorageInfoDTO getStorageData() throws IOException {
        StorageInfoDTO info = new StorageInfoDTO();
        info.setUsedSpace(getCurrentUsedMemory()/(1.0 * Configuration.MEMORY_UNIT_COEFFICIENT));
        info.setAllSpace(Configuration.MEMORY_SIZE_IN_DEFAULT_MEMORY_UNIT);
        info.setMemoryUnit(Configuration.DEFAULT_MEMORY_UNIT);
        return info;
    }

    public static long getCurrentUsedMemory() throws IOException {
        Path folder = Paths.get(Configuration.DIRECTORY_PATH);
        return Files.walk(folder, FileVisitOption.FOLLOW_LINKS)
                .filter(p -> p.toFile().isFile())
                .mapToLong(p -> p.toFile().length())
                .sum();
    }

}
