package me.hoseok.ppmtool.validator;

import lombok.RequiredArgsConstructor;
import me.hoseok.ppmtool.domain.Project;
import me.hoseok.ppmtool.repository.ProjectRepository;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class ProjectValidator implements Validator {

    private final ProjectRepository projectRepository;

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.isAssignableFrom(Project.class);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Project project = (Project)o;
        if (projectRepository.existsByProjectIdentifier(project.getProjectIdentifier())) {
            errors.rejectValue("projectIdentifier","invalid.projectIdentifier","ProjectIdentifier is already exists");
        }
    }
}
