package me.hoseok.ppmtool.service;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;

@Service
public class MapValidationErrorsService {

    public ResponseEntity MapValidationErrorsService(Errors errors){
        if(errors.hasErrors()){

            Map<String, String> errorMap = new HashMap<>();

            for(FieldError error: errors.getFieldErrors()){
                errorMap.put(error.getField(), error.getDefaultMessage());
            }
            return ResponseEntity.badRequest().body(errorMap);
        }

        return null;
    }
}
