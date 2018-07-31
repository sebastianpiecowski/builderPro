package com.pl.exaco.builder_pro.repository;

import com.pl.exaco.builder_pro.entity.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FileRepository extends JpaRepository<FileEntity, Integer> {

    FileEntity findById(int id);

    List<FileEntity> findByBuildId_Id(int id);

    List<FileEntity> findByBuildIdProjectId_Id(int id);

    FileEntity findFirstByBuildId_IdOrderByUploadDate(int buildId);

    @Query(nativeQuery = true, value = "SELECT COUNT(f.Id) FROM [dbo].[File] AS f WHERE f.BuildId = :id")
    Integer countOfBuild(@Param("id") int id);
}
