package com.pl.exaco.builder_pro.service;

import com.pl.exaco.builder_pro.entity.ProjectEntity;
import com.pl.exaco.builder_pro.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    public List<ProjectEntity> getAllProjects() {
        return projectRepository.findAll();
    }

    public ProjectEntity getProject(int id) {
        return projectRepository.findById(id);
    }

    public ProjectEntity getProjectByName(String name) {
        return  projectRepository.findByName(name);
    }

    public Integer findOrAddProject(String projectName) {
        ProjectEntity project=projectRepository.findByName(projectName);
        if(project==null) {
            ProjectEntity projectEntity=new ProjectEntity();
            projectEntity.set
        }
    }
}
