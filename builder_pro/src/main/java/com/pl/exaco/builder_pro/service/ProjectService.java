package com.pl.exaco.builder_pro.service;

import com.pl.exaco.builder_pro.dto.FlavorDTO;
import com.pl.exaco.builder_pro.dto.FlavorFileDTO;
import com.pl.exaco.builder_pro.dto.ProjectDTO;
import com.pl.exaco.builder_pro.dto.TypeDTO;
import com.pl.exaco.builder_pro.entity.BuildEntity;
import com.pl.exaco.builder_pro.entity.FileEntity;
import com.pl.exaco.builder_pro.entity.ProjectEntity;
import com.pl.exaco.builder_pro.repository.BuildRepository;
import com.pl.exaco.builder_pro.repository.FileRepository;
import com.pl.exaco.builder_pro.repository.ProjectRepository;
import com.pl.exaco.builder_pro.utils.appNameParser;
import com.pl.exaco.builder_pro.utils.datetimeParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private BuildRepository buildRepository;
    @Autowired
    private FileRepository fileRespository;

    public List<ProjectEntity> getAllProjects() {
        return projectRepository.findAll();
    }

    public ProjectDTO getProject(int id) {
        ProjectEntity projectEntity = projectRepository.findById(id);
        ProjectDTO projectDTO = new ProjectDTO();
        if (projectEntity != null) {
            projectDTO.setProjectName(projectEntity.getName());
            System.out.println(projectDTO.getProjectName());


            List<BuildEntity> listOfBuilds = buildRepository.findByProjectId_Id(projectEntity.getId());
            //set unikalnych buildow flavor(nazwy)
            Set<String> buildSet = new HashSet<>();
            listOfBuilds.forEach(e -> {
                buildSet.add(e.getFlavorDictId().getName());
            });
            buildSet.forEach(e -> {
                List<BuildEntity> selectedBuild = new ArrayList<>();
                selectedBuild = buildRepository.findByFlavorDictId_Name(e);
                FlavorDTO flavor = new FlavorDTO();
                flavor.setName(e);
                List<TypeDTO> types = new ArrayList<>();
                for (BuildEntity build : selectedBuild) {
                    TypeDTO typeDTO = new TypeDTO();
                    typeDTO.setName(build.getBuildDictId().getName());
                    List<FileEntity> files = fileRespository.findByBuildId_Id(build.getId());
                    List<FlavorFileDTO> flavorFiles = new ArrayList<>();

                    //dodawanie wszystkich plików dla buildu DTO
                    files.forEach(f -> {
                        FlavorFileDTO flavorFile = new FlavorFileDTO();
                        flavorFile.setId(f.getId());
                        flavorFile.setFileName(f.getFileName());
                        flavorFile.setUploadTimestamp(f.getUploadDate().getTime());
                        flavorFile.setUploadDate(datetimeParser.parseToString(f.getUploadDate()));
                        try {
                            flavorFile.setStatusName(f.getStatusId().getName());
                        } catch (NullPointerException ne) {
                            //optional
                        }
                        flavorFiles.add(flavorFile);
                    });
                    typeDTO.setFiles(flavorFiles);
                    types.add(typeDTO);
                }

                flavor.setTypes(types);
                List<FlavorDTO> flavors = new ArrayList<>();
                flavors.add(flavor);
                projectDTO.setFlavors(flavors);

            });

        }
        return projectDTO;
    }

    public ProjectEntity getProjectByName(String name) {
        return projectRepository.findByName(name);
    }

    public ProjectEntity findOrAddProject(Map<String, String> projectInfo) {
        ProjectEntity project = projectRepository.findByName(projectInfo.get(appNameParser.PROJECT_NAME));
        if (project == null) {
            ProjectEntity projectEntity = new ProjectEntity();
            projectEntity.setName(projectInfo.get(appNameParser.PROJECT_NAME));
            projectEntity.setLastBuildFileName(projectInfo.get(appNameParser.FILE_NAME));
            projectRepository.save(projectEntity);
            return projectEntity;
        } else {
            return project;
        }
    }
}
