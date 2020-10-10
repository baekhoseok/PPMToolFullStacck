package me.hoseok.ppmtool.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter @Getter
@AllArgsConstructor
public class ProjectNotFountExceptionResponse {
    private String projectNotFound;
}
