package com.pl.exaco.builder_pro.controller;

import com.pl.exaco.builder_pro.entity.ProjectEntity;
import com.pl.exaco.builder_pro.service.ProjectService;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/builderpro/v1")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @GetMapping(value = "/project")
    public ResponseEntity<List<ProjectEntity>> getProjects(){	
        return new ResponseEntity<>(projectService.getAllProjects(), HttpStatus.OK);
    }
    
    @GetMapping(value = "/project/{id}")
    public ResponseEntity<ProjectEntity> getProject(@PathVariable("id") Integer id) {
        return new ResponseEntity<>(projectService.getProject(id), HttpStatus.OK);
    }
}
