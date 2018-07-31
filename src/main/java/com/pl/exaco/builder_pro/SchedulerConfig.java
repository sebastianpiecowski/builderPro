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
import java.util.List;

@Configuration
@EnableScheduling
public class SchedulerConfig {

    @Autowired
    private FileService fileService;

    @Autowired
    private DiawiService diawiService;

    @Scheduled(cron = "0 1 */2 * * ?")
    public void scheduleTaskUsingCronExpression() {

        List<FileDTO> files = fileService.getFiles();

        for (FileDTO file : files) {
            if (file != null) {
                try {
                    File physicalFile = new File(com.pl.exaco.builder_pro.utils.Configuration.DIRECTORY_PATH + file.getFileName());
                    StatusResponse status = diawiService.uploadFileAndWaitForResponse(physicalFile);
                    if (status.getStatus() == 2000) {
                        fileService.updateFileDiawiLink(file.getId(), status.getLink());
                    }
                } catch (Exception ex) {
                    System.err.println("File does not exists or was changed.");
                }
            }
        }
    }
}
