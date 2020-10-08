package me.hoseok.ppmtool.exceptions;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class ProjectIdExceptionResponse {

    private String projectIdentifier;

    public ProjectIdExceptionResponse(String projectIdentifier) {
        this.projectIdentifier = projectIdentifier;
    }
}
