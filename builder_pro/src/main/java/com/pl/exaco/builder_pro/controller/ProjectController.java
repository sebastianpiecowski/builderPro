package com.pl.exaco.builder_pro.controller;

import com.pl.exaco.builder_pro.entity.ProjectEntity;
import com.pl.exaco.builder_pro.service.ProjectService;
import java.util.List;

import com.pl.exaco.builder_pro.utils.AuthenticationHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/builderpro/v1")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @GetMapping(value = "/project")
    public ResponseEntity<List<ProjectEntity>> getProjects(@RequestHeader(AuthenticationHelper.HEADER_FIELD) String token){
        try {
            AuthenticationHelper.Authorize(token);
        } catch (Exception e){
            return  new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(projectService.getAllProjects(), HttpStatus.OK);
    }
    
    @GetMapping(value = "/project/{id}")
    public ResponseEntity<ProjectEntity> getProject(@PathVariable("id") Integer id, @RequestHeader(AuthenticationHelper.HEADER_FIELD) String token) {
        try {
            AuthenticationHelper.Authorize(token);
        } catch (Exception e){
            return  new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(projectService.getProject(id), HttpStatus.OK);
    }
}
