package com.example.demo.global.except;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

@RestControllerAdvice
public class ExceptHandler {
    @ExceptionHandler(ExceptionGenerator.class)
    private ResponseEntity<ExceptionResponse> exceptionHandling(ExceptionGenerator e) {
        if (e.getSuspensionEndTime() != null)
            return ResponseEntity.status(e.getStatusCode())
                                 .body(new SuspensionExceptionResponse(e));

        return ResponseEntity.status(e.getStatusCode())
                             .body(new ExceptionResponse(e));
    }

    @ExceptionHandler({SQLIntegrityConstraintViolationException.class, MethodArgumentNotValidException.class})
    private ResponseEntity<ExceptionResponse> checkDuplicateUnit() {
        ExceptionGenerator e = new ExceptionGenerator(StatusEnum.DUPLICATE_UNIT);
        return ResponseEntity.status(e.getStatusCode()).body(new ExceptionResponse(e));
    }

}
