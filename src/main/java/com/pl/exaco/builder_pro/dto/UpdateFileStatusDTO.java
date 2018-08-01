package com.pl.exaco.builder_pro.dto;

import lombok.Data;

@Data
public class UpdateFileStatusDTO {
    private Integer fileId;
    private String  statusName;
}
