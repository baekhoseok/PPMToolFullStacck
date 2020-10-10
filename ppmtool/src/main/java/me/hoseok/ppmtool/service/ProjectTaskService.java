package me.hoseok.ppmtool.service;

import lombok.RequiredArgsConstructor;
import me.hoseok.ppmtool.domain.Backlog;
import me.hoseok.ppmtool.domain.ProjectTask;
import me.hoseok.ppmtool.exceptions.ProjectNotFoundException;
import me.hoseok.ppmtool.repository.BacklogRepository;
import me.hoseok.ppmtool.repository.ProjectRepository;
import me.hoseok.ppmtool.repository.ProjectTaskRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ProjectTaskService {

    private final ProjectTaskRepository projectTaskRepository;
    private final BacklogRepository backlogRepository;
    private final ProjectRepository projectRepository;

    public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask) {

        Backlog backlog = backlogRepository.findByProjectIdentifier(projectIdentifier);
        if (backlog == null) {
            throw new ProjectNotFoundException("Project dose not exists");
        }
        projectTask.setBacklog(backlog);
        Integer ptSequence = backlog.getPTSequence();
        ptSequence++;
        backlog.setPTSequence(ptSequence);
        projectTask.setProjectSequence(projectIdentifier +"-"+ ptSequence);
        projectTask.setProjectIdentifier(projectIdentifier);

        if (projectTask.getPriority() == null || projectTask.getPriority() == 0 ) {
            projectTask.setPriority(3);
        }
        if (projectTask.getStatus() == null || projectTask.getStatus() == "") {
            projectTask.setStatus("TO_DO");
        }

        return projectTaskRepository.save(projectTask);
    }

    @Transactional(readOnly = true)
    public List<ProjectTask> findByBacklogId(String backlog_id) {
        if (!projectRepository.existsByProjectIdentifier(backlog_id)) {
            throw new ProjectNotFoundException("Project dose not found");
        }
        return projectTaskRepository.findByProjectIdentifierOrderByPriority(backlog_id);
    }

    @Transactional(readOnly = true)
    public ProjectTask getProjectTask(String backlog_id, String pt_id) {
        Backlog backlog = backlogRepository.findByProjectIdentifier(backlog_id);
        if (backlog == null) {
            throw new ProjectNotFoundException("Project dose not found");
        }
        ProjectTask projectTask = projectTaskRepository.findByProjectSequence(pt_id);
        if (projectTask == null) {
            throw new ProjectNotFoundException("Project Task dose not found");
        }

        if (!projectTask.getBacklog().getProjectIdentifier().equals(backlog_id)) {
            throw new ProjectNotFoundException("Project Task dose not found in Project");
        }
        return projectTask;
    }

    public ProjectTask updateProjectTask(String backlog_id, String pt_id, ProjectTask updateTask) {
        ProjectTask projectTask = getProjectTask(backlog_id, pt_id);
        projectTask = updateTask;
        projectTaskRepository.save(projectTask);
        return projectTask;
    }


    public void deleteProjectTask(String backlog_id, String pt_id) {
        ProjectTask projectTask = getProjectTask(backlog_id, pt_id);


//        Backlog backlog = projectTask.getBacklog();
//        List<ProjectTask> projectTasks = backlog.getProjectTask();
//        projectTasks.remove(projectTask);
//        backlogRepository.save(backlog);
        projectTaskRepository.delete(projectTask);
    }
}
