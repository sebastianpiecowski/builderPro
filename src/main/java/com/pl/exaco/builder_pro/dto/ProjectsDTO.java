package com.pl.exaco.builder_pro.dto;

import com.pl.exaco.builder_pro.entity.ProjectEntity;
import lombok.Data;

import java.util.List;
@Data
public class ProjectsDTO {
    List<ProjectEntity> projects;
}
