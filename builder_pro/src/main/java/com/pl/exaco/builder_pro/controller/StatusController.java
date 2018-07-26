package com.pl.exaco.builder_pro.controller;

import com.pl.exaco.builder_pro.dto.StatusDictDto;
import com.pl.exaco.builder_pro.service.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/builderpro/v1")
public class StatusController {

    @Autowired
    private StatusService statusService;

    @GetMapping(value = "/status")
    public ResponseEntity<StatusDictDto> getProjects() {
        return new ResponseEntity<>(statusService.getStatusDict(), HttpStatus.OK);
    }

}

