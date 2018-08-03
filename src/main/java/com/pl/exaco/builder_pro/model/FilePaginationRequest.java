package com.pl.exaco.builder_pro.model;

import lombok.Data;

@Data
public class FilePaginationRequest {
    Integer page=null;
    Integer size=null;
    String sortColumn=null;

}
