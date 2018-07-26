package com.pl.exaco.builder_pro.service;

import com.pl.exaco.builder_pro.entity.BuildEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pl.exaco.builder_pro.repository.BuildRepository;

import java.util.List;

@Service
public class BuildService {

	@Autowired
	BuildRepository buildRepository;

	public List<BuildEntity> getAll(){
		return buildRepository.findAll();
	};

	public BuildEntity findById(int id) {
		return buildRepository.findById(id);
	};

}
