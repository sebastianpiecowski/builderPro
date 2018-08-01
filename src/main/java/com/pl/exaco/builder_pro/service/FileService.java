package com.pl.exaco.builder_pro.service;

import com.pl.exaco.builder_pro.dto.FileDTO;
import com.pl.exaco.builder_pro.dto.UpdateFileStatusDTO;
import com.pl.exaco.builder_pro.entity.BuildEntity;
import com.pl.exaco.builder_pro.entity.FileEntity;
import com.pl.exaco.builder_pro.entity.StatusDictEntity;
import com.pl.exaco.builder_pro.repository.FileRepository;
import com.pl.exaco.builder_pro.repository.StatusRepository;
import com.pl.exaco.builder_pro.utils.AppNameParser;
import com.pl.exaco.builder_pro.utils.Configuration;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

@Service
public class FileService {

    private static final int NUMBER_OF_DAYS = 3;
    @Autowired
    private FileRepository fileRepository;
    @Autowired
    private StatusRepository statusRepository;
    private ModelMapper modelMapper;

    public List<FileDTO> getFiles() {
        List<FileDTO> list = new ArrayList<>();
        List<FileEntity> files = fileRepository.findAll();
        files.forEach(e -> {
            list.add(new FileDTO(e));
        });
        return list;
    }

    public FileEntity getLastFileQuery(int id) {
        return fileRepository.findFirstByBuildId_IdOrderByUploadDate(id);
    }

    public FileDTO getFile(int id) {
        return new FileDTO(fileRepository.findById(id));
    }

    public Integer addFile(BuildEntity buildEntity, Map<String, String> applicationInfo) {
        Integer count = fileRepository.countOfBuild(buildEntity.getId());
        if (count < 3) {
            FileEntity fileEntity = addApkToStorage(buildEntity, applicationInfo);
            return fileEntity.getId();
        } else {
            FileEntity fileEntity = fileRepository.findFirstByBuildId_IdOrderByUploadDate(buildEntity.getId());
            File file = new File(Configuration.DIRECTORY_PATH + fileEntity.getFileName());
            if (file.delete()) {
                fileRepository.delete(fileEntity);
                FileEntity newFile = addApkToStorage(buildEntity, applicationInfo);
                return newFile.getId();
            } else {
                return -1;
            }

        }
    }

    private FileEntity addApkToStorage(BuildEntity buildEntity, Map<String, String> applicationInfo) {
        FileEntity fileEntity = new FileEntity();
        fileEntity.setBuildId(buildEntity);
        fileEntity.setFileName(applicationInfo.get(AppNameParser.FILE_NAME));
        fileEntity.setDiawiUrl(applicationInfo.get(AppNameParser.DIAWI_URL));
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
        return fileEntity;
    }


    public UpdateFileStatusDTO updateFileStatus(int fileId, int statusId) throws Exception {
        FileEntity fileEntity = fileRepository.findById(fileId);
        StatusDictEntity statusDictEntity = statusRepository.findById(statusId);
        if (statusDictEntity != null) {
            fileEntity.setStatusId(statusDictEntity);
            fileRepository.save(fileEntity);
            UpdateFileStatusDTO result = new UpdateFileStatusDTO();
            result.setFileId(fileId);
            result.setStatusName(statusDictEntity.getName());
            return result;
        } else {
            throw new Exception("The given status does not exists.");
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

    public List<FileEntity> getFilesByProjectId(int id) {
        return fileRepository.findByBuildIdProjectId_Id(id);
    }

    public FileEntity findById(int id) {
        return fileRepository.findById(id);
    }

    public void deleteFile(int id) {
        fileRepository.deleteById(id);
    }

}
