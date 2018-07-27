package com.pl.exaco.builder_pro.repository;

import com.pl.exaco.builder_pro.entity.ProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import com.pl.exaco.builder_pro.entity.BuildEntity;

public interface BuildRepository extends JpaRepository<BuildEntity, Integer>{

    BuildEntity findById(int id);

    BuildEntity findByProjectId_IdAndBuildDictId_NameAndFlavorDictId_Name(int projectId, String buildDictName, String flavorDictName);
}
