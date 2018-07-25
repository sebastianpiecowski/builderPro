package com.pl.exaco.builder_pro.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pl.exaco.builder_pro.repository.BuildRepository;

@Service
public class BuildService {

	@Autowired
	BuildRepository buildRepository;
}
