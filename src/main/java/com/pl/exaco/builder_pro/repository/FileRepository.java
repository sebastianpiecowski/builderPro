package com.pl.exaco.builder_pro.repository;

import com.pl.exaco.builder_pro.entity.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FileRepository extends JpaRepository<FileEntity, Integer> {

    FileEntity findById(int id);
    List<FileEntity> findByBuildId_Id(int id);
    List<FileEntity> findByBuildIdProjectId_Id(int id);

    //3 ostatnie pliku buildu
    //    List<FileEntity> findByBuildId_IdOrderByUploadDateDesc(int id);

}
