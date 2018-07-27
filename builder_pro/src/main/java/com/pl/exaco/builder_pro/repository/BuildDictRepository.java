package com.pl.exaco.builder_pro.repository;

import com.pl.exaco.builder_pro.entity.BuildDictEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BuildDictRepository extends JpaRepository<BuildDictEntity, Integer> {

    BuildDictEntity findByName(String name);
}
