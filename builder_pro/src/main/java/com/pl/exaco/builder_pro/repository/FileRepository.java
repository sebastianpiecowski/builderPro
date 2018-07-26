package com.pl.exaco.builder_pro.repository;

import com.pl.exaco.builder_pro.entity.ProjectEntity;
import com.pl.exaco.builder_pro.service.FileService;
import org.springframework.data.jpa.repository.JpaRepository;

import com.pl.exaco.builder_pro.entity.FileEntity;

public interface FileRepository extends JpaRepository<FileEntity, Integer>{
    FileEntity findById(int id);
}
