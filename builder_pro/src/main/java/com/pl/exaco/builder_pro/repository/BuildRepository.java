package com.pl.exaco.builder_pro.repository;

import com.pl.exaco.builder_pro.entity.BuildEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BuildRepository extends JpaRepository<BuildEntity, Integer> {

    BuildEntity findById(int id);

    BuildEntity findByProjectId_IdAndBuildDictId_NameAndFlavorDictId_Name(int projectId, String buildDictName, String flavorDictName);

    List<BuildEntity> findByProjectId_Id(int projectId);

    List<BuildEntity> findByFlavorDictId_Name(String name);

}
