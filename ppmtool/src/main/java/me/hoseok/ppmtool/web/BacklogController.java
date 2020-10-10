package me.hoseok.ppmtool.web;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import me.hoseok.ppmtool.domain.Backlog;
import me.hoseok.ppmtool.domain.ProjectTask;
import me.hoseok.ppmtool.service.MapValidationErrorsService;
import me.hoseok.ppmtool.service.ProjectTaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/backlog")
@CrossOrigin
@RequiredArgsConstructor
public class BacklogController {

    private final ProjectTaskService projectTaskService;
    private final MapValidationErrorsService mapValidationErrorsService;

    @PostMapping("/{backlog_id}")
    public ResponseEntity addProjectTask(@PathVariable String backlog_id, @Valid @RequestBody ProjectTask projectTask, Errors errors) {
        ResponseEntity errorMap = mapValidationErrorsService.MapValidationErrorsService(errors);
        if (errorMap != null) {
            return errorMap;
        }

        ProjectTask projectTask1 = projectTaskService.addProjectTask(backlog_id, projectTask);
        return ResponseEntity.status(HttpStatus.CREATED).body(projectTask1);
    }

    @GetMapping("/{backlog_id}")
    public List<ProjectTask> getProjectTasks(@PathVariable String backlog_id) {
        return projectTaskService.findByBacklogId(backlog_id);
    }

    @GetMapping("/{backlog_id}/{pt_id}")
    public ProjectTask getProjectTask(@PathVariable String backlog_id, @PathVariable String pt_id) {

        return projectTaskService.getProjectTask(backlog_id, pt_id);
    }

    @PatchMapping("/{backlog_id}/{pt_id}")
    public ResponseEntity updateProjectTask(@PathVariable String backlog_id, @PathVariable String pt_id,
            @Valid @RequestBody ProjectTask projectTask, Errors errors) {
        ResponseEntity errorMap = mapValidationErrorsService.MapValidationErrorsService(errors);
        if (errorMap != null) {
            return errorMap;
        }
        return ResponseEntity.ok(projectTaskService.updateProjectTask(backlog_id, pt_id, projectTask));
    }

    @DeleteMapping("/{backlog_id}/{pt_id}")
    public ResponseEntity deleteProjectTask(@PathVariable String backlog_id, @PathVariable String pt_id) {
        projectTaskService.deleteProjectTask(backlog_id, pt_id);
        return ResponseEntity.ok().body("Project delete successfully");
    }

}
