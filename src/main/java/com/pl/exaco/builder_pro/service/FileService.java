package com.pl.exaco.builder_pro.service;

import com.pl.exaco.builder_pro.entity.BuildEntity;
import com.pl.exaco.builder_pro.entity.FileEntity;
import com.pl.exaco.builder_pro.dto.FileDTO;
import com.pl.exaco.builder_pro.entity.FileEntity;
import com.pl.exaco.builder_pro.entity.StatusDictEntity;
import com.pl.exaco.builder_pro.repository.FileRepository;
import com.pl.exaco.builder_pro.repository.StatusRepository;
import com.pl.exaco.builder_pro.utils.appNameParser;
import org.apache.james.mime4j.field.datetime.DateTime;
import org.apache.tomcat.jni.File;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import java.sql.Timestamp;
import java.util.*;

@Service
public class FileService {

    @Autowired
    private FileRepository fileRepository;
    @Autowired
    private StatusRepository statusRepository;

    private static final int NUMBER_OF_DAYS = 3;

    private ModelMapper modelMapper;

    public List<FileDTO> getFiles() {
        List<FileDTO> list = new ArrayList<>();
        List<FileEntity> files = fileRepository.findAll();
        files.forEach(e -> {
            list.add(new FileDTO(e));
        });
        return list;
    }

    public FileDTO getFile(int id) {
        return new FileDTO(fileRepository.findById(id));
    }

    public Integer addFile(BuildEntity buildEntity, Map<String, String> applicationInfo) {

        FileEntity fileEntity = new FileEntity();
        fileEntity.setBuildId(buildEntity);
        fileEntity.setFileName(applicationInfo.get(appNameParser.FILE_NAME));
        fileEntity.setDiawiUrl(applicationInfo.get(appNameParser.DIAWI_URL));
        Timestamp uploadTimestamp = new Timestamp(System.currentTimeMillis());
        fileEntity.setUploadDate(uploadTimestamp);
        Calendar cal = Calendar.getInstance();
        cal.setTime(uploadTimestamp);
        cal.add(Calendar.DAY_OF_WEEK, NUMBER_OF_DAYS);
        Timestamp expirationTimestamp = new Timestamp(cal.getTime().getTime());
        fileEntity.setExpirationDate(expirationTimestamp);
        fileEntity.setStatusId(statusRepository.findById(1));
        //TODO SET STATUS_ID
        fileRepository.save(fileEntity);
        return fileEntity.getId();
    }


    public void updateFileStatus(int fileId, int statusId) {
        FileEntity fileEntity = fileRepository.findById(fileId);
        if (fileEntity != null) {
            StatusDictEntity statusDictEntity = statusRepository.findById(statusId);
            if (statusDictEntity != null) {
                fileEntity.setStatusId(statusDictEntity);
                fileRepository.save(fileEntity);
            }
        }
    }

    public void updateFileDiawiLink(int fileId, String diawiLink) {
        FileEntity fileEntity = fileRepository.findById(fileId);
        if (fileEntity != null) {
            fileEntity.setDiawiUrl(diawiLink);
            Timestamp expirationTimestamp = new Timestamp(System.currentTimeMillis());
            Calendar cal = Calendar.getInstance();
            cal.setTime(expirationTimestamp);
            cal.add(Calendar.DAY_OF_WEEK, NUMBER_OF_DAYS);
            expirationTimestamp.setTime(cal.getTime().getTime());
            fileEntity.setExpirationDate(expirationTimestamp);
            fileRepository.save(fileEntity);
        }
    }

    public List<FileEntity> getFilesByProjectId(int id){
        return fileRepository.findByBuildIdProjectId_Id(id);
    }

    public FileEntity findById(int id) {
        return fileRepository.findById(id);
    }

}
