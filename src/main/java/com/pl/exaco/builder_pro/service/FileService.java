package com.pl.exaco.builder_pro.service;

import com.pl.exaco.builder_pro.dto.FileDTO;
import com.pl.exaco.builder_pro.dto.UpdateFileStatusDTO;
import com.pl.exaco.builder_pro.entity.BuildEntity;
import com.pl.exaco.builder_pro.entity.FileEntity;
import com.pl.exaco.builder_pro.entity.ProjectEntity;
import com.pl.exaco.builder_pro.entity.StatusDictEntity;
import com.pl.exaco.builder_pro.model.FilePaginationRequest;
import com.pl.exaco.builder_pro.repository.BuildRepository;
import com.pl.exaco.builder_pro.repository.FileRepository;
import com.pl.exaco.builder_pro.repository.StatusRepository;
import com.pl.exaco.builder_pro.utils.ApkInfo;
import com.pl.exaco.builder_pro.utils.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Service
public class FileService {

    private static final int NUMBER_OF_DAYS = 3;
    @Autowired
    private FileRepository fileRepository;
    @Autowired
    private StatusRepository statusRepository;
    @Autowired
    private BuildRepository buildRepository;
    @Autowired
    private ProjectService projectService;
    @Autowired
    private BuildService buildService;

    public List<FileDTO> getActiveFiles(){
        List<FileEntity> files = fileRepository.findAll();
        List<FileDTO> list = new ArrayList<>();

        files.forEach(e -> {
            list.add(new FileDTO(e));
        });
        return list;
    }
    public List<FileDTO> getFiles(FilePaginationRequest request) {


        int pageOfFiles;
        int sizeOfFiles;
        String sortingColumn = null;
        try {
            pageOfFiles = request.getPage();
        } catch (NullPointerException npe) {
            pageOfFiles = 0;
        }
        try {
            if (request.getSortColumn() != null) {
                sortingColumn = request.getSortColumn();
            } else {
                sortingColumn = "Id";
            }
            sizeOfFiles = request.getSize();
        } catch (NullPointerException npe) {
            sizeOfFiles = 5;
        }
        Page<FileEntity> files = fileRepository.findAll(PageRequest.of(pageOfFiles, sizeOfFiles, Sort.by(sortingColumn)));
        List<FileDTO> list = new ArrayList<>();

        files.forEach(e -> {
            list.add(new FileDTO(e));
        });
        return list;
    }

    public FileEntity getLastFileQuery(int id) {
        return fileRepository.findFirstByBuildId_IdOrderByUploadDate(id);
    }

    public FileDTO getFile(int id) {
        FileEntity fileEntity = fileRepository.findById(id);
        if(fileEntity == null) return null;
        return new FileDTO(fileEntity);
    }

    @Transactional
    public Integer storeApk(ApkInfo info) {
        ProjectEntity project = projectService.findOrAddProject(info);
        BuildEntity build = buildService.findOrAddBuild(project, info);
        return addFile(build, info);
    }


    private Integer addFile(BuildEntity buildEntity, ApkInfo info) {
        Integer count = fileRepository.countOfBuild(buildEntity.getId());
        if (count < 3) {
            FileEntity fileEntity = addApkToStorage(buildEntity, info);
            return fileEntity.getId();
        } else {
            FileEntity fileEntity = fileRepository.findFirstByBuildId_IdOrderByUploadDate(buildEntity.getId());
            File file = new File(Configuration.DIRECTORY_PATH + fileEntity.getFileName());
            if (file.delete()) {
                deleteFile(fileEntity.getId());
                FileEntity newFile = addApkToStorage(buildEntity, info);
                return newFile.getId();
            } else {
                return -1;
            }

        }
    }

    private FileEntity addApkToStorage(BuildEntity buildEntity, ApkInfo info) {
        FileEntity dbFile = fileRepository.findByFileName(info.getFileName());
        if (dbFile == null) {
            dbFile = new FileEntity();
            dbFile.setBuildId(buildEntity);
            dbFile.setFileName(info.getFileName());
            dbFile.setStatusId(statusRepository.findById(1));
        }
        dbFile.setDiawiUrl(info.getDiawiUrl());
        Timestamp uploadTimestamp = new Timestamp(System.currentTimeMillis());
        dbFile.setUploadDate(uploadTimestamp);
        Calendar cal = Calendar.getInstance();
        cal.setTime(uploadTimestamp);
        cal.add(Calendar.DAY_OF_WEEK, NUMBER_OF_DAYS);
        Timestamp expirationTimestamp = new Timestamp(cal.getTime().getTime());
        dbFile.setExpirationDate(expirationTimestamp);
        fileRepository.save(dbFile);
        return dbFile;
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
        FileEntity file = fileRepository.findById(id);
        if (file != null) {
            Integer build = file.getBuildId().getId();
            fileRepository.deleteById(id);
            if (fileRepository.countOfBuild(build) == 0) {
                buildRepository.findById(id);
            }
        }

    }

}

