package com.pl.exaco.builder_pro.service;

import com.pl.exaco.builder_pro.entity.BuildEntity;
import com.pl.exaco.builder_pro.entity.ProjectEntity;
import com.pl.exaco.builder_pro.repository.BuildDictRepository;
import com.pl.exaco.builder_pro.repository.FlavorDictRepository;
import com.pl.exaco.builder_pro.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pl.exaco.builder_pro.repository.BuildRepository;

import java.util.List;
import java.util.Map;

@Service
public class BuildService {

	@Autowired
	BuildRepository buildRepository;
	@Autowired
	BuildDictRepository buildDictRepository;
	@Autowired
	FlavorDictRepository flavorDictRepository;
	@Autowired
	ProjectRepository projectRepository;
	public List<BuildEntity> getAll(){
		return buildRepository.findAll();
	};

	public BuildEntity findById(int id) {
		return buildRepository.findById(id);
	};

    public BuildEntity findOrAddBuild(ProjectEntity projectId, Map<String, String> buildInfo) {
    	BuildEntity buildEntity=buildRepository.findByProjectId_IdAndBuildDictId_NameAndFlavorDictId_Name(projectId.getId(), buildInfo.get("BuildType"), buildInfo.get("Flavor"));
    	if(buildEntity==null) {
    		BuildEntity newBuildEntity=new BuildEntity();
    		newBuildEntity.setBuildDictId(buildDictRepository.findByName(buildInfo.get("BuildType")));
    		newBuildEntity.setFlavorDictId(flavorDictRepository.findByName(buildInfo.get("Flavor")));
    		newBuildEntity.setProjectId(projectId);

    		buildRepository.save(newBuildEntity);
    		return newBuildEntity;
		}
		else {
    		return buildEntity;
		}
    };

}
