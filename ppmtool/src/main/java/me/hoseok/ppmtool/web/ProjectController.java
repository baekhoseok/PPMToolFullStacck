package me.hoseok.ppmtool.web;

import lombok.RequiredArgsConstructor;
import me.hoseok.ppmtool.domain.Project;
import me.hoseok.ppmtool.exceptions.ProjectIdException;
import me.hoseok.ppmtool.service.MapValidationErrorsService;
import me.hoseok.ppmtool.service.ProjectService;
import me.hoseok.ppmtool.validator.ProjectValidator;
import org.apache.catalina.LifecycleState;
import org.apache.coyote.Response;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@CrossOrigin
public class ProjectController {

    private final ProjectService projectService;
    private final ModelMapper modelMapper;
    private final MapValidationErrorsService mapValidationErrorsService;
    private final ProjectValidator projectValidator;

//    @InitBinder("project")
//    public void projectInitBinder(WebDataBinder webDataBinder) {
//        webDataBinder.addValidators(projectValidator);
//    }

    @PostMapping("/project")
    public ResponseEntity createNewProject(@Valid @RequestBody Project project, Errors errors) throws DataIntegrityViolationException {

        ResponseEntity<?> errorMap = mapValidationErrorsService.MapValidationErrorsService(errors);
        if(errorMap!=null)return errorMap;
        Project newProject = projectService.createOrUpdate(project);
        return ResponseEntity.status(HttpStatus.CREATED).body(newProject);
    }

    @GetMapping("/project/{projectIdentifier}")
    public ResponseEntity getProject(@PathVariable String projectIdentifier) {
        Project project = projectService.getProject(projectIdentifier);
        return ResponseEntity.ok(project);
    }

    @GetMapping("/projects")
    public ResponseEntity getProjects() {
        List<Project> all = projectService.findAll();
        return ResponseEntity.ok(all);
    }

    @DeleteMapping("/project/{projectIdentifier}")
    public ResponseEntity deleteProject(@PathVariable String projectIdentifier) {
        projectService.deleteByProjectIdentifier(projectIdentifier);
        return ResponseEntity.ok().build();
    }



}
