package com.pl.exaco.builder_pro.dto;

import com.pl.exaco.builder_pro.entity.StatusDictEntity;
import lombok.Data;

import java.util.List;

@Data
public class StatusDictDto {
    private List<StatusDictEntity> statuses;

}
