package com.pl.exaco.builder_pro.service;

import com.pl.exaco.builder_pro.entity.FlavorDictEntity;
import com.pl.exaco.builder_pro.repository.FlavorDictRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FlavorService {

    @Autowired
    private FlavorDictRepository flavorDictRepository;

    public List<FlavorDictEntity> getFlavorDict() {
        return flavorDictRepository.findAll();
    }
}
