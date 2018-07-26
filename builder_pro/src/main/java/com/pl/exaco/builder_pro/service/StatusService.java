package com.pl.exaco.builder_pro.service;

import com.pl.exaco.builder_pro.dto.StatusDictDto;
import com.pl.exaco.builder_pro.repository.StatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service

public class StatusService {

    @Autowired
    private StatusRepository statusRepository;

    public StatusDictDto getStatusDict() {

        StatusDictDto statusDictDto = new StatusDictDto();
        statusDictDto.setStatuses(statusRepository.findAll());
        return statusDictDto;
    }
}
