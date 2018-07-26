package com.pl.exaco.builder_pro.repository;

import com.pl.exaco.builder_pro.entity.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<FileEntity, Integer> {

    FileEntity findById(int id);
}
