package com.pl.exaco.builder_pro.dto;

import lombok.Data;

import java.util.List;

@Data
public class TypeDTO {
    private String name;
    private List<FlavorFileDTO> files;
}
