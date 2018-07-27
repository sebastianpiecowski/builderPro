package com.pl.exaco.builder_pro.dto;

import lombok.Data;

import java.util.List;
@Data
public class ProjectDTO {
    private String projectName;
    private List<FlavorDTO> flavors;
}
