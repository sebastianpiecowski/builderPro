package com.pl.exaco.builder_pro.controller;

import com.pl.exaco.builder_pro.dto.ProjectDTO;
import com.pl.exaco.builder_pro.dto.ProjectsDTO;
import com.pl.exaco.builder_pro.service.FileService;
import com.pl.exaco.builder_pro.service.ProjectService;
import com.pl.exaco.builder_pro.utils.AuthenticationHelper;
import com.pl.exaco.builder_pro.utils.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = Configuration.VERSION)
public class ProjectController {

    @Autowired
    private ProjectService projectService;
    @Autowired
    private FileService fileService;

    @GetMapping(value = "/project")
    private ResponseEntity<ProjectsDTO> getProjects(@RequestHeader(AuthenticationHelper.HEADER_FIELD) String token) {
        try {
            AuthenticationHelper.Authorize(token);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(projectService.getAllProjects(), HttpStatus.OK);
    }


    @GetMapping(value = "/project/{id}")
    private ResponseEntity<ProjectDTO> getProject(@PathVariable("id") Integer id, @RequestHeader(AuthenticationHelper.HEADER_FIELD) String token) {
        try {
            AuthenticationHelper.Authorize(token);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        ProjectDTO project = projectService.getProject(id);
        if(project == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(project, HttpStatus.OK);
    }
}
