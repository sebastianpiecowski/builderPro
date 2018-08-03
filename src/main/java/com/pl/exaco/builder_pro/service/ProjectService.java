package com.pl.exaco.builder_pro.service;

import com.pl.exaco.builder_pro.dto.*;
import com.pl.exaco.builder_pro.entity.BuildEntity;
import com.pl.exaco.builder_pro.entity.FileEntity;
import com.pl.exaco.builder_pro.entity.ProjectEntity;
import com.pl.exaco.builder_pro.repository.BuildRepository;
import com.pl.exaco.builder_pro.repository.FileRepository;
import com.pl.exaco.builder_pro.repository.ProjectRepository;
import com.pl.exaco.builder_pro.utils.AppNameParser;
import com.pl.exaco.builder_pro.utils.DatetimeParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private BuildRepository buildRepository;
    @Autowired
    private FileRepository fileRespository;

    public ProjectsDTO getAllProjects() {
        ProjectsDTO projectsDTO = new ProjectsDTO();
        projectsDTO.setProjects(projectRepository.findAll());

        return projectsDTO;
    }

    public ProjectEntity getProjectByName(String name) {
        return projectRepository.findByName(name);
    }

    public ProjectEntity findOrAddProject(Map<String, String> projectInfo) {
        ProjectEntity project = projectRepository.findByName(projectInfo.get(AppNameParser.PROJECT_NAME));
        if (project == null) {
            ProjectEntity projectEntity = new ProjectEntity();
            projectEntity.setName(projectInfo.get(AppNameParser.PROJECT_NAME));
            projectEntity.setLastBuildFilename(projectInfo.get(AppNameParser.FILE_NAME));
            projectRepository.save(projectEntity);
            return projectEntity;
        } else {
            project.setLastBuildFilename(projectInfo.get(AppNameParser.FILE_NAME));
            return project;
        }
    }

    public ProjectDTO getProject(int id) {
        ProjectEntity projectEntity = projectRepository.findById(id);
        ProjectDTO projectDTO = null;
        List<FlavorDTO> flavors = new ArrayList<>();
        if (projectEntity != null) {
            projectDTO = new ProjectDTO();
            projectDTO.setProjectName(projectEntity.getName());
            HashMap<String, HashMap<String, List<BuildEntity>>> map = getProjectHashMap(projectEntity);

            map.forEach((flavorName, buildTypes) -> {
                FlavorDTO flavor = new FlavorDTO();
                flavor.setTypes(new ArrayList<>());
                flavor.setName(flavorName);

                buildTypes.forEach((buildName, fileRepresentation) -> {
                    TypeDTO type = new TypeDTO();
                    type.setName(buildName);
                    List<FlavorFileDTO> buildFiles = getFilesForListOfBuilds(fileRepresentation);
                    if(!buildFiles.isEmpty()) {
                        type.setFiles(buildFiles);
                        flavor.getTypes().add(type);
                    }
                });
                if(!flavor.getTypes().isEmpty()) {
                    flavors.add(flavor);
                }
            });
            if(!flavors.isEmpty()) {
                projectDTO.setFlavors(flavors);
            }
        }
        return projectDTO;
    }

    private List<FlavorFileDTO> getFilesForListOfBuilds(List<BuildEntity> fileRepresentation) {
        List<FlavorFileDTO> flavorFiles = new ArrayList<>();
        for (BuildEntity buildEntity : fileRepresentation) {
            List<FileEntity> files = fileRespository.findByBuildId_Id(buildEntity.getId());
            files.forEach(f -> {
                FlavorFileDTO flavorFile = new FlavorFileDTO();
                flavorFile.setId(f.getId());
                flavorFile.setFileName(f.getFileName());
                flavorFile.setUploadTimestamp(f.getUploadDate().getTime());
                flavorFile.setUploadDate(DatetimeParser.parseToString(f.getUploadDate()));
                try {
                    flavorFile.setStatusName(f.getStatusId().getName());
                } catch (NullPointerException ne) {
                    //optional
                }
                flavorFiles.add(flavorFile);
            });
        }
        return flavorFiles;
    }

    private HashMap<String, HashMap<String, List<BuildEntity>>> getProjectHashMap(ProjectEntity projectEntity) {
        //TODO optimize (use streams?)
        List<BuildEntity> builds = buildRepository.findByProjectId_Id(projectEntity.getId());

        // create {flavorName, {buildName, List<BuildEntity>} }
        HashMap<String, HashMap<String, List<BuildEntity>>> flavors = new HashMap<>();
        builds.stream().forEach(buildEntity -> {

            //Extract useful fields
            String flavorName = buildEntity.getFlavorDictId().getName();
            String buildName = buildEntity.getBuildDictId().getName();
            HashMap<String, List<BuildEntity>> buildTypes;
            List<BuildEntity> fileRepresentations;

            // create {buildName, List<BuildEntity> }
            if (flavors.containsKey(flavorName)) {
                buildTypes = flavors.get(flavorName);
                if (buildTypes.containsKey(buildName)) {
                    buildTypes.get(buildName).add(buildEntity);
                } else {
                    fileRepresentations = new ArrayList<>();
                    fileRepresentations.add(buildEntity);
                    buildTypes.put(buildName, fileRepresentations);
                }
            } else {
                buildTypes = new HashMap<>();
                fileRepresentations = new ArrayList<>();
                fileRepresentations.add(buildEntity);
                buildTypes.put(buildName, fileRepresentations);
                flavors.put(flavorName, buildTypes);

            }
        });
        return flavors;
    }


}
