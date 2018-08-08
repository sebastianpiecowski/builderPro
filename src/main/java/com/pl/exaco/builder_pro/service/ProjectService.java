package com.pl.exaco.builder_pro.service;

import com.pl.exaco.builder_pro.dto.*;
import com.pl.exaco.builder_pro.entity.BuildEntity;
import com.pl.exaco.builder_pro.entity.FileEntity;
import com.pl.exaco.builder_pro.entity.ProjectEntity;
import com.pl.exaco.builder_pro.repository.BuildRepository;
import com.pl.exaco.builder_pro.repository.FileRepository;
import com.pl.exaco.builder_pro.repository.ProjectRepository;
import com.pl.exaco.builder_pro.utils.ApkInfo;
import com.pl.exaco.builder_pro.utils.Configuration;
import com.pl.exaco.builder_pro.utils.DatetimeParser;
import net.dongliu.apk.parser.ApkParser;
import net.dongliu.apk.parser.bean.Icon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

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

    public ProjectEntity findOrAddProject(ApkInfo info) {
        String apkFilePath = Configuration.DIRECTORY_PATH + info.getFileName();
        String iconExtension = null;
        String iconStoragePath;

        try (ApkParser parser = new ApkParser(new File(apkFilePath))) {
            final Icon iconFile = parser.getIconFile();
            String iconInternalPath = iconFile.getPath();
            iconExtension = iconInternalPath.substring(iconInternalPath.lastIndexOf('.'));
            iconStoragePath = Configuration.ICONS_DIRECTORY_PATH + info.getProjectName() + iconExtension;
            new File(Configuration.ICONS_DIRECTORY_PATH).mkdirs();
            File file = new File(iconStoragePath);
            FileOutputStream out = new FileOutputStream(file);
            out.write(Objects.requireNonNull(iconFile.getData()));
            out.close();
        } catch (Exception e) {
            iconStoragePath = null;
        }

        ProjectEntity project = projectRepository.findByName(info.getProjectName());
        if (project == null) {
            project = new ProjectEntity();
            project.setName(info.getProjectName());
            project.setLastBuildFilename(info.getFileName());
        }
        if (iconStoragePath != null) {
            project.setThumbnail(Configuration.API_PATH_TO_ICONS + info.getProjectName() + iconExtension);
        }

        projectRepository.save(project);
        return project;
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
                    if (!buildFiles.isEmpty()) {
                        type.setFiles(buildFiles);
                        flavor.getTypes().add(type);
                    }
                });
                if (!flavor.getTypes().isEmpty()) {
                    flavors.add(flavor);
                }
            });
            if (!flavors.isEmpty()) {
                projectDTO.setFlavors(flavors);
            }
        }
        return projectDTO;
    }

    private List<FlavorFileDTO> getFilesForListOfBuilds(List<BuildEntity> fileRepresentation) {
        List<FlavorFileDTO> flavorFiles = new ArrayList<>();
        for (BuildEntity buildEntity : fileRepresentation) {
            List<FileEntity> files = fileRespository.findByBuildId_Id(buildEntity.getId());
            System.out.println(files.size());
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
