package com.pl.exaco.builder_pro;

import com.pl.exaco.builder_pro.dto.FileDTO;
import com.pl.exaco.builder_pro.service.FileService;
import com.pl.exaco.builder_pro.utils.diawi.DiawiService;
import com.pl.exaco.builder_pro.utils.diawi.StatusResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import static com.pl.exaco.builder_pro.utils.Configuration.DIRECTORY_PATH;

//@Configuration
//@EnableScheduling
//public class SchedulerConfig {
//
//    @Autowired
//    private FileService fileService;
//
//    @Autowired
//    private DiawiService diawiService;
//
//
//    @Scheduled(cron = "0 0 0 */2 * ?")
//    public void refreshDiawiLinks() {
//
//        List<FileDTO> files = fileService.getActiveFiles();
//
//        for (FileDTO file : files) {
//            if (file != null) {
//                try {
//                    File physicalFile = new File(DIRECTORY_PATH + file.getFileName());
//                    StatusResponse status = diawiService.uploadFileAndWaitForResponse(physicalFile);
//                    if (status.getStatus() == 2000) {
//                        fileService.updateFileDiawiLink(file.getId(), status.getLink());
//                    }
//                } catch (Exception ex) {
//                    System.err.println("File does not exists or was changed.");
//                }
//            }
//        }
//    }
//
//    @Scheduled(cron = "0 0 * * * ?")
//    public void synchronizeDatabaseWithStorage() {
//        File directory = new File(DIRECTORY_PATH);
//        List<FileDTO> filesInDatabase = fileService.getActiveFiles();
//        File[] filesInStorage = directory.listFiles();
//
//        Arrays.stream(filesInStorage).forEach(f -> {
//            if (!filesInDatabase.stream().anyMatch(f2 -> f2.getFileName().equalsIgnoreCase(f.getName()))) {
//                f.delete();
//            }
//        });
//
//        filesInDatabase.forEach(f -> {
//            if (!Arrays.stream(filesInStorage).anyMatch(fis -> fis.getName().equalsIgnoreCase(f.getFileName()))) {
//                fileService.deleteFile(f.getId());
//            }
//        });
//    }
//}
