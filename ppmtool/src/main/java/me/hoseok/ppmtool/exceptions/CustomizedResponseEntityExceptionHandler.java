package me.hoseok.ppmtool.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler
    public final ResponseEntity handleProjectIdAlreadyExistsException(ProjectIdException e, WebRequest webRequest) {
        ProjectIdExceptionResponse exceptionResponse = new ProjectIdExceptionResponse(e.getMessage());
        return ResponseEntity.badRequest().body(exceptionResponse);
    }
}
