package com.pl.exaco.builder_pro.service;

import com.pl.exaco.builder_pro.entity.BuildDictEntity;
import com.pl.exaco.builder_pro.entity.BuildEntity;
import com.pl.exaco.builder_pro.entity.ProjectEntity;
import com.pl.exaco.builder_pro.repository.BuildDictRepository;
import com.pl.exaco.builder_pro.repository.FlavorDictRepository;
import com.pl.exaco.builder_pro.repository.ProjectRepository;
import com.pl.exaco.builder_pro.utils.AppNameParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pl.exaco.builder_pro.repository.BuildRepository;

import java.util.List;
import java.util.Map;

@Service
public class BuildService {

    @Autowired
    private BuildRepository buildRepository;
    @Autowired
    private BuildDictRepository buildDictRepository;
    @Autowired
    private FlavorDictRepository flavorDictRepository;
    @Autowired
    public ProjectRepository projectRepository;

    public List<BuildEntity> getAll() {
        return buildRepository.findAll();
    }

    public BuildEntity findById(int id) {
        return buildRepository.findById(id);
    }

    public BuildEntity findOrAddBuild(ProjectEntity project, Map<String, String> buildInfo) {
        BuildEntity buildEntity = buildRepository.findByProjectId_IdAndBuildDictId_NameAndFlavorDictId_Name(project.getId(), buildInfo.get(AppNameParser.BUILD_TYPE), buildInfo.get(AppNameParser.FLAVOR));
        if (buildEntity == null) {
            BuildEntity newBuildEntity = new BuildEntity();
            newBuildEntity.setProjectId(project);
            newBuildEntity.setBuildDictId(buildDictRepository.findByName(buildInfo.get(AppNameParser.BUILD_TYPE)));
            newBuildEntity.setFlavorDictId(flavorDictRepository.findByName(buildInfo.get(AppNameParser.FLAVOR)));
            buildRepository.save(newBuildEntity);
            return newBuildEntity;
        } else {
            return buildEntity;
        }
    }

    public List<BuildDictEntity> getBuildDict(){
        return buildDictRepository.findAll();
    }


}
