package com.pl.exaco.builder_pro.service;

import com.pl.exaco.builder_pro.entity.BuildEntity;
import com.pl.exaco.builder_pro.entity.ProjectEntity;
import com.pl.exaco.builder_pro.repository.BuildDictRepository;
import com.pl.exaco.builder_pro.repository.FlavorDictRepository;
import com.pl.exaco.builder_pro.repository.ProjectRepository;
import com.pl.exaco.builder_pro.utils.appNameParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pl.exaco.builder_pro.repository.BuildRepository;

import java.util.List;
import java.util.Map;

@Service
public class BuildService {

    @Autowired
    BuildRepository buildRepository;
    @Autowired
    BuildDictRepository buildDictRepository;
    @Autowired
    FlavorDictRepository flavorDictRepository;
    @Autowired
    ProjectRepository projectRepository;

    public List<BuildEntity> getAll() {
        return buildRepository.findAll();
    }

    public BuildEntity findById(int id) {
        return buildRepository.findById(id);
    }

    public BuildEntity findOrAddBuild(ProjectEntity project, Map<String, String> buildInfo) {
        BuildEntity buildEntity = buildRepository.findByProjectId_IdAndBuildDictId_NameAndFlavorDictId_Name(project.getId(), buildInfo.get(appNameParser.BUILD_TYPE), buildInfo.get(appNameParser.FLAVOR));
        if (buildEntity == null) {
            BuildEntity newBuildEntity = new BuildEntity();
            newBuildEntity.setProjectId(project);
            newBuildEntity.setBuildDictId(buildDictRepository.findByName(buildInfo.get(appNameParser.BUILD_TYPE)));
            newBuildEntity.setFlavorDictId(flavorDictRepository.findByName(buildInfo.get(appNameParser.FLAVOR)));
            buildRepository.save(newBuildEntity);
            return newBuildEntity;
        } else {
            return buildEntity;
        }
    }

    ;

}
