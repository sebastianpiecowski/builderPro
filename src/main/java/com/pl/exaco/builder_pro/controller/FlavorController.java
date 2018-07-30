package com.pl.exaco.builder_pro.controller;

import com.pl.exaco.builder_pro.entity.FlavorDictEntity;
import com.pl.exaco.builder_pro.utils.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pl.exaco.builder_pro.service.FlavorService;

import java.util.List;

@RestController
@RequestMapping(value = Configuration.VERSION)
public class FlavorController {

	@Autowired
	private FlavorService flavorService;

	@GetMapping(value = "/flavor/dict")
	private ResponseEntity<List<FlavorDictEntity>> getFlavorDict(){
		return new ResponseEntity<>(flavorService.getFlavorDict(), HttpStatus.OK);
	}
}
