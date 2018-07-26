package com.pl.exaco.builder_pro.service;

import com.pl.exaco.builder_pro.entity.FileEntity;
import com.pl.exaco.builder_pro.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileService {

    @Autowired
    private FileRepository fileRepository;

    public List<FileEntity> getFiles() {
        return fileRepository.findAll();
    }

    public FileEntity getFile(int id) {
        return fileRepository.findById(id);
    }
}
