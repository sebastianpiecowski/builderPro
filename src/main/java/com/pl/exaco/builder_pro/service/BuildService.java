package com.pl.exaco.builder_pro.service;

import com.pl.exaco.builder_pro.entity.BuildDictEntity;
import com.pl.exaco.builder_pro.entity.BuildEntity;
import com.pl.exaco.builder_pro.entity.FileEntity;
import com.pl.exaco.builder_pro.entity.ProjectEntity;
import com.pl.exaco.builder_pro.repository.*;
import com.pl.exaco.builder_pro.utils.ApkInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BuildService {

    @Autowired
    private BuildRepository buildRepository;
    @Autowired
    private FileRepository fileRepository;
    @Autowired
    private BuildDictRepository buildDictRepository;
    @Autowired
    private FlavorDictRepository flavorDictRepository;
    @Autowired
    public ProjectRepository projectRepository;
    @Autowired
    private FileService fileService;

    public List<BuildEntity> getAll() {
        return buildRepository.findAll();
    }

    public BuildEntity findById(int id) {
        return buildRepository.findById(id);
    }

    public BuildEntity findOrAddBuild(ProjectEntity project, ApkInfo info) {
        BuildEntity buildEntity = buildRepository.findByProjectId_IdAndBuildDictId_NameAndFlavorDictId_Name(project.getId(), info.getBuildType(), info.getFlavor());
        if (buildEntity == null) {
            BuildEntity newBuildEntity = new BuildEntity();
            newBuildEntity.setProjectId(project);
            newBuildEntity.setBuildDictId(buildDictRepository.findByName(info.getBuildType()));
            newBuildEntity.setFlavorDictId(flavorDictRepository.findByName(info.getFlavor()));
            buildRepository.save(newBuildEntity);
            return newBuildEntity;
        } else {
            return buildEntity;
        }
    }

    public List<BuildDictEntity> getBuildDict(){
        return buildDictRepository.findAll();
    }


    public void deleteBuild(int id) {
        List<FileEntity> files = fileRepository.findByBuildId_Id(id);
        for (FileEntity file:files) {
            fileService.deleteFileFromDbAndStorage(file.getId());
        }
    }
}
