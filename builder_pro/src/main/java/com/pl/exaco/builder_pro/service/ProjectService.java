package com.pl.exaco.builder_pro.service;

import com.pl.exaco.builder_pro.entity.ProjectEntity;
import com.pl.exaco.builder_pro.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

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

    public Integer findOrAddProject(Map<String, String> projectInfo) {
        ProjectEntity project=projectRepository.findByName(projectInfo.get("ProjectName"));
        if(project==null) {
            ProjectEntity projectEntity=new ProjectEntity();
            projectEntity.setName(projectInfo.get("ProjectName"));
            projectEntity.setLastBuildFileName(projectInfo.get("FileName"));
            projectRepository.save(projectEntity);
            return projectEntity.getId();
        }
        else {
            return project.getId();
        }
    }
}
