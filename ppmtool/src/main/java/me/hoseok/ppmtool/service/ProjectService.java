package me.hoseok.ppmtool.service;

import lombok.RequiredArgsConstructor;
import me.hoseok.ppmtool.domain.Project;
import me.hoseok.ppmtool.exceptions.ProjectIdException;
import me.hoseok.ppmtool.repository.ProjectRepository;
import org.springframework.dao.DataIntegrityViolationException;
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
        String projectIdentifier = project.getProjectIdentifier().toUpperCase();
        try {
            project.setProjectIdentifier(projectIdentifier);
            //transaction 완료시 commit 발생되고 transaction 은 함수가 리턴된후 완료된다.
            //따라서 try catch 구문에서 porjectIdentifier 중복 exception 을 잡을 수 없다.
            //그러므로 save 동시에 flush 를 해주어 transaction 완료 전 중복 exception 을 발생시킬 수 있다.
            return projectRepository.saveAndFlush(project);
        } catch (Exception e) {
            throw new ProjectIdException("ProjectId '"+projectIdentifier+"' dose not exists");
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
