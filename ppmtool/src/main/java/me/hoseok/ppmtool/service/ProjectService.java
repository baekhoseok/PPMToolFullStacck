package me.hoseok.ppmtool.service;

import lombok.RequiredArgsConstructor;
import me.hoseok.ppmtool.domain.Project;
import me.hoseok.ppmtool.exceptions.ProjectIdException;
import me.hoseok.ppmtool.repository.ProjectRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ProjectService {
    
    private final ProjectRepository projectRepository;

    public Project createOrUpdate(Project project) {
        try {
            project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
            return projectRepository.save(project);
        } catch (Exception e) {
            throw new ProjectIdException("ProjectId '"+project.getProjectIdentifier()+"'already exists");
        }
    }

    @Transactional(readOnly = true)
    public Project getProject(String projectIdentifier) {
        return projectRepository.findByProjectIdentifier(projectIdentifier).orElseThrow(
                () -> new ProjectIdException("ProjectId '"+projectIdentifier+"' dose not exists")
        );

    }

    @Transactional(readOnly = true)
    public List<Project> findAll() {
        return projectRepository.findAll();
    }

    public void deleteByProjectIdentifier(String projectIdentifier) {
        long count = projectRepository.deleteByProjectIdentifier(projectIdentifier);
        System.out.println("===================");
        System.out.println("count = " + count);
        if (count<=0) {
            throw new ProjectIdException("ProjectId '"+projectIdentifier+"' dose not exists");
        }
    }
}
