package com.pl.exaco.builder_pro.controller;

import com.pl.exaco.builder_pro.dto.StatusDictDTO;
import com.pl.exaco.builder_pro.service.StatusService;
import com.pl.exaco.builder_pro.utils.AuthenticationHelper;
import com.pl.exaco.builder_pro.utils.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = Configuration.VERSION)
public class StatusController {

    @Autowired
    private StatusService statusService;

    @GetMapping(value = "/status")
    private ResponseEntity<StatusDictDTO> getProjects(@RequestHeader(AuthenticationHelper.HEADER_FIELD) String token) {
        try {
            AuthenticationHelper.Authorize(token);
        } catch (Exception e){
            return  new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(statusService.getStatusDict(), HttpStatus.OK);
    }

}

