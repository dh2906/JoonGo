package com.example.demo.global.except;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ExceptionResponse {
    Integer exceptionStatusCode;
    String exceptionStatusName;
    String exceptionStatusMessage;

    public ExceptionResponse(ExceptionGenerator e) {
        this.exceptionStatusCode = e.getStatusCode();
        this.exceptionStatusName = e.getStatusName();
        this.exceptionStatusMessage = e.getStatusMessage();
    }
}
