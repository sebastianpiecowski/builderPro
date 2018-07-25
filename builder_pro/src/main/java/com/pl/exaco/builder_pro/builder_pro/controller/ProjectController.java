package controller;

import entity.ProjectEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import service.ProjectService;

import java.util.List;

@RestController
@RequestMapping(value = "builderpro")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @GetMapping(value = "/project")
    public ResponseEntity<List<ProjectEntity>> getProjects(){

        return new ResponseEntity<>(projectService.getAllProjects(), HttpStatus.OK);

    }
}
