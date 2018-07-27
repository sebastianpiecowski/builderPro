package com.pl.exaco.builder_pro.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pl.exaco.builder_pro.entity.BuildEntity;

public interface BuildRepository extends JpaRepository<BuildEntity, Integer>{

    BuildEntity findById(int id);

    BuildEntity findByProjectIdAndBuildDictId_NameAndFlavorDictId_Name(int id, String buildName, String flavorName);
}
