package com.pl.exaco.builder_pro.repository;

import com.pl.exaco.builder_pro.entity.FlavorDictEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlavorDictRepository extends JpaRepository<FlavorDictEntity, Integer>{

    FlavorDictEntity findByName(String name);
}
