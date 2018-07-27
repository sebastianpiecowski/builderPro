package com.pl.exaco.builder_pro.dto;

import lombok.Data;

import java.util.List;

@Data
public class FlavorDTO {
    private String name;
    private List<TypeDTO> types;
}
