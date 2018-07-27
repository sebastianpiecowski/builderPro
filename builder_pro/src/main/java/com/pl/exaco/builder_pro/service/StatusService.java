package com.pl.exaco.builder_pro.service;

import com.pl.exaco.builder_pro.dto.StatusDictDTO;
import com.pl.exaco.builder_pro.repository.StatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatusService {

    @Autowired
    private StatusRepository statusRepository;

    public StatusDictDTO getStatusDict() {
        StatusDictDTO statusDictDTO = new StatusDictDTO();
        statusDictDTO.setStatuses(statusRepository.findAll());
        return statusDictDTO;
    }
}
