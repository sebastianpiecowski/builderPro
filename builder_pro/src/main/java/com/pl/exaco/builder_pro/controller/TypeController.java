package com.pl.exaco.builder_pro.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pl.exaco.builder_pro.service.TypeService;

@RestController
@RequestMapping(value = "/builderpro/v1")
public class TypeController {

	@Autowired
	private TypeService typeService;
}
