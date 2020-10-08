package me.hoseok.ppmtool.repository;

import me.hoseok.ppmtool.domain.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    boolean existsByProjectName(String projectName);

    Optional<Project> findByProjectIdentifier(String projectIdentifier);

    boolean existsByProjectIdentifier(String projectIdentifier);

    long deleteByProjectIdentifier(String projectIdentifier);
}
