package repository;

import entity.ProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface ProjectRepository extends JpaRepository<ProjectEntity, Integer> {

    List<ProjectEntity> getAll();

}
