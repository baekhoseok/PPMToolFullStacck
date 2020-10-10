package me.hoseok.ppmtool.repository;

import me.hoseok.ppmtool.domain.Backlog;
import me.hoseok.ppmtool.domain.ProjectTask;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectTaskRepository extends JpaRepository<ProjectTask, Long> {
    List<ProjectTask> findByProjectIdentifierOrderByPriority(String backlog_id);

    ProjectTask findByProjectSequence(String pt_id);

    ProjectTask findByProjectSequenceAndProjectIdentifier(String pt_id, String backlog_id);
}
