package com.pl.exaco.builder_pro.dto;


import lombok.Data;

@Data
public class FlavorFileDTO {
    private int id;
    private String fileName;
    private Long uploadTimestamp;
    private String uploadDate;
    private String statusName;
}
