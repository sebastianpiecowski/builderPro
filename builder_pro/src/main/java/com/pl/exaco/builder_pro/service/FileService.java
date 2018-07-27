package com.pl.exaco.builder_pro.service;

import com.pl.exaco.builder_pro.entity.BuildEntity;
import com.pl.exaco.builder_pro.entity.FileEntity;
import com.pl.exaco.builder_pro.dto.FileDTO;
import com.pl.exaco.builder_pro.entity.FileEntity;
import com.pl.exaco.builder_pro.repository.FileRepository;
import org.apache.james.mime4j.field.datetime.DateTime;
import org.apache.tomcat.jni.File;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

@Service
public class FileService {

    @Autowired
    private FileRepository fileRepository;

    private ModelMapper modelMapper;
    public List<FileDTO> getFiles() {
        modelMapper=new ModelMapper();
        List<FileDTO> list=new ArrayList<>();
        List<FileEntity> files=fileRepository.findAll();
        files.forEach(e-> {
            list.add(modelMapper.map(e, FileDTO.class));
        });
        return list;
    }

    public FileDTO getFile(int id) {
        modelMapper=new ModelMapper();
        return modelMapper.map(fileRepository.findById(id),FileDTO.class);
    }

    public FileEntity addFile(BuildEntity buildEntity, Map<String,String> applicationInfo) {

        FileEntity fileEntity = new FileEntity();
        fileEntity.setBuildId(buildEntity);
        fileEntity.setFileName(applicationInfo.get("FileName"));
        fileEntity.setDiawiUrl(applicationInfo.get("DiawiUrl"));
        Timestamp uploadTimestamp = new Timestamp(System.currentTimeMillis());
        fileEntity.setUploadDate(uploadTimestamp);
        Calendar cal = Calendar.getInstance();
        cal.setTime(uploadTimestamp);
        cal.add(Calendar.DAY_OF_WEEK, 3);
        Timestamp expirationTimestamp = new Timestamp(cal.getTime().getTime());
        fileEntity.setExpirationDate(expirationTimestamp);

        //TODO statusId
        fileRepository.save(fileEntity);
        return fileEntity;
    }

//	public void updateFileStatus(int fileId, int statusId){
//		FileEntity fileEntity = fileRepository.findById(fileId);
//		fileEntity.setStatusId(statusId);
//		fileRepository.save(fileEntity);
//	}

}
