package com.pl.exaco.builder_pro.service;

import com.pl.exaco.builder_pro.entity.ProjectEntity;
import com.pl.exaco.builder_pro.repository.ProjectRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {

	@Autowired
	private ProjectRepository projectRepository;

	public List<ProjectEntity> getAllProjects() {
		return projectRepository.findAll();
	}
	
	public ProjectEntity getProject(int id) {
		return projectRepository.findById(id);
	}

}
