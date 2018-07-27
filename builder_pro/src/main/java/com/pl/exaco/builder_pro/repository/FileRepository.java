package com.pl.exaco.builder_pro.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pl.exaco.builder_pro.entity.FileEntity;

import java.util.List;

public interface FileRepository extends JpaRepository<FileEntity, Integer> {

    FileEntity findById(int id);

    List<FileEntity> findByBuildId_Id(int id);
}
