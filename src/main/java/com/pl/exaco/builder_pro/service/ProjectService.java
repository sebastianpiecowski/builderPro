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

    public ProjectDTO getProject(int id) {
        ProjectEntity projectEntity = projectRepository.findById(id);
        ProjectDTO projectDTO = new ProjectDTO();
        List<FlavorDTO> flavors = new ArrayList<>();
        if (projectEntity != null) {
            projectDTO.setProjectName(projectEntity.getName());
            System.out.println(projectDTO.getProjectName());


            List<BuildEntity> listOfBuilds = buildRepository.findByProjectId_Id(projectEntity.getId());
            //set unikalnych buildow flavor(nazwy)


            listOfBuilds.forEach(e -> {
                        FlavorDTO flavor = new FlavorDTO();
                        flavor.setName(e.getFlavorDictId().getName());

                        List<TypeDTO> types = new ArrayList<>();

                        TypeDTO typeDTO = new TypeDTO();
                        typeDTO.setName(e.getBuildDictId().getName());
                        //metoda repo do sortowania po upload date - wybieranie 3 ostatnich plików buildu
                        //List<FileEntity> files = fileRespository.findByBuildId_IdOrderByUploadDateDesc(e.getId());
                        List<FileEntity> files = fileRespository.findByBuildId_Id(e.getId());
                        List<FlavorFileDTO> flavorFiles = new ArrayList<>();

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
                        typeDTO.setFiles(flavorFiles);
                        types.add(typeDTO);
                        flavor.setTypes(types);
                        flavors.add(flavor);

                    }
            );
            projectDTO.setFlavors(flavors);
        }
        return projectDTO;
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
            return project;
        }
    }
}
