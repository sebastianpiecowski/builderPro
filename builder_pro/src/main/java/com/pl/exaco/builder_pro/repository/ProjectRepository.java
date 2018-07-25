package com.pl.exaco.builder_pro.repository;

import com.pl.exaco.builder_pro.entity.ProjectEntity;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<ProjectEntity, Integer> {
	ProjectEntity findById(int id);
}
