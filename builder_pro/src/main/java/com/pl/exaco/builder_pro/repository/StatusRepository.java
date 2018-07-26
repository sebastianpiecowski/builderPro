package com.pl.exaco.builder_pro.repository;

import com.pl.exaco.builder_pro.entity.StatusDictEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusRepository extends JpaRepository<StatusDictEntity, Integer> {
}
