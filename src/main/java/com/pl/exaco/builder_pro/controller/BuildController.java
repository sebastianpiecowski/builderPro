package com.pl.exaco.builder_pro.controller;

import com.pl.exaco.builder_pro.entity.BuildDictEntity;
import com.pl.exaco.builder_pro.utils.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pl.exaco.builder_pro.service.BuildService;

import java.util.List;

@RestController
@RequestMapping(value = Configuration.VERSION)
public class BuildController {

	@Autowired
	private BuildService buildService;

	@GetMapping(value = "/build/dict")
	private ResponseEntity<List<BuildDictEntity>> getBuildDict(){
		return new ResponseEntity<>(buildService.getBuildDict(), HttpStatus.OK);
	}

}
